package cz.cvut.fit.tjv.bitjvrocnamicsemestralclient.data;

import cz.cvut.fit.tjv.bitjvrocnamicsemestralclient.model.CarDto;
import cz.cvut.fit.tjv.bitjvrocnamicsemestralclient.model.DriverDto;
import cz.cvut.fit.tjv.bitjvrocnamicsemestralclient.model.DriverWebModel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class DriverClient extends AbstractClient<DriverWebModel, DriverDto, Long>{


    protected final String FT_URI = ONE_URI + "/car";

    public DriverClient (@Value("${backend_url}") String backendUrl) {
        super(backendUrl, "/driver", DriverWebModel.class);
    }

    @Override
    protected Mono<DriverWebModel> readById(Long id) {
        Mono<DriverDto> driverDtoMono = webClient.get()
                .uri(ONE_URI, id)
                .retrieve()
                .bodyToMono(DriverDto.class);




        Mono<Set<CarDto>> carDto = webClient.get()
                .uri(FT_URI, id)
                .retrieve()
                .bodyToFlux(CarDto.class)
                .collect(Collectors.toSet());


        return Mono.zip(driverDtoMono,  carDto)
               .map(tuple -> new DriverWebModel(tuple.getT1(), tuple.getT2()));

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
