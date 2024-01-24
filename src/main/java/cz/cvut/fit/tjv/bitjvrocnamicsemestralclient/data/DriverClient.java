package cz.cvut.fit.tjv.bitjvrocnamicsemestralclient.data;

import cz.cvut.fit.tjv.bitjvrocnamicsemestralclient.model.CarDto;
import cz.cvut.fit.tjv.bitjvrocnamicsemestralclient.model.CompanyDto;
import cz.cvut.fit.tjv.bitjvrocnamicsemestralclient.model.DriverDto;
import cz.cvut.fit.tjv.bitjvrocnamicsemestralclient.model.DriverWebModel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class DriverClient extends AbstractClient<DriverWebModel, DriverDto, Long>{


    protected final String DRV_URI = ONE_URI + "/car";
    protected final String COM_URI = ONE_URI + "/company";

    public DriverClient (@Value("${backend_url}") String backendUrl) {
        super(backendUrl, "/driver", DriverWebModel.class);
    }


    public void deleteDriversWithoutCars() {
        webClient.get()
                .uri("/driver/deleteWithoutCar")
                .retrieve()
                .bodyToMono(Void.class)
                .block();
    }

    @Override
    protected Mono<DriverWebModel> readById(Long id) {
        Mono<DriverDto> driverDtoMono = webClient.get()
                .uri(ONE_URI, id)
                .retrieve()
                .bodyToMono(DriverDto.class);




        Mono<Set<CarDto>> carDto = webClient.get()
                .uri(DRV_URI, id)
                .retrieve()
                .bodyToFlux(CarDto.class)
                .collect(Collectors.toSet());

        Mono<Set<CompanyDto>> companyDto = webClient.get()
                .uri(COM_URI, id)
                .retrieve()
                .bodyToFlux(CompanyDto.class)
                .collect(Collectors.toSet());



        return Mono.zip(driverDtoMono,  carDto, companyDto)
               .map(tuple -> new DriverWebModel(tuple.getT1(), tuple.getT2(),tuple.getT3()));

    }



    @Override
    public DriverWebModel newWM() {
        return new DriverWebModel();
    }

    @Override
    public DriverWebModel newWM(DriverDto dto) {
        return new DriverWebModel(dto);
    }
}
