package cz.cvut.fit.tjv.bitjvrocnamicsemestralclient.data;

import cz.cvut.fit.tjv.bitjvrocnamicsemestralclient.model.*;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class CompanyClient extends AbstractClient<CompanyWebModel, CompanyDto, Long>{


    protected static final String DRIVER_URI = ONE_URI + "/driver";

    public CompanyClient (@Value("${backend_url}") String backendUrl) {
        super(backendUrl, "/company", CompanyWebModel.class);
    }




    public Mono<CompanyWebModel> showAttend(Long id) {
        Mono<CompanyWebModel> companyMono = webClient.get()
                .uri(ONE_URI, id)
                .retrieve()
                .bodyToMono(CompanyWebModel.class)
                ;

        Mono<Set<DriverDto>> driversMono = webClient.get()
                .uri(DRIVER_URI, id)
                .retrieve()
                .bodyToFlux(DriverDto.class)
                .collect(Collectors.toSet());

        return Mono.zip(companyMono, driversMono)
                .flatMap(tuple -> {
                    CompanyWebModel company= tuple.getT1();
                    Set<DriverDto> driverDtos = tuple.getT2();

                    driverDtos.stream()
                            .map(driverDto -> driverDto.id)
                            .forEach(company::addDriverId);

                    return Mono.just(company);
                });
    }

    public Mono<Void> attend(Long id, Set<Long> newDisIds) {
        return webClient.get()
                .uri(DRIVER_URI, id)
                .retrieve()
                .bodyToFlux(DriverDto.class)
                .collect(Collectors.toSet())
                .flatMap(previousDrivers -> {
                    Set<Long> previousAttendedIds = previousDrivers.stream()
                            .map(driver -> driver.id)
                            .collect(Collectors.toSet());

                    Set<Long> delListIds = new HashSet<>(previousAttendedIds);
                    Set<Long> addListIds = new HashSet<>(newDisIds);

                    delListIds.removeAll(newDisIds);
                    addListIds.removeAll(previousAttendedIds);

                    Flux<Void> deleteOperations = Flux.fromIterable(delListIds)
                            .flatMap(disId -> webClient.delete()
                                    .uri(DRIVER_URI + "/{driverId}", id, disId)
                                    .retrieve()
                                    .toBodilessEntity()
                                    .then()
                            );

                    Flux<Void> addOperations = Flux.fromIterable(addListIds)
                            .flatMap(disId -> webClient.put()
                                    .uri(DRIVER_URI+ "/{driverId}", id, disId)
                                    .retrieve()
                                    .toBodilessEntity()
                                    .then()
                            );

                    return Flux.concat(deleteOperations, addOperations).then();
                });
    }


    /*@Override
    protected Mono<CompanyWebModel> readById(Long id) {
        return webClient.get()
                .uri(ONE_URI, id)
                .retrieve()
                .bodyToMono(CompanyDto.class)
                .map(companyDto -> new CompanyWebModel(companyDto));
    }*/

    @Override
    protected Mono<CompanyWebModel> readById(Long id) {
        Mono<CompanyDto> companyMono = webClient.get()
                .uri(ONE_URI, id)
                .retrieve()
                .bodyToMono(CompanyDto.class);

        Mono<Set<DriverDto>> driversMono = webClient.get()
                .uri(DRIVER_URI, id)
                .retrieve()
                .bodyToFlux(DriverDto.class)
                .collect(Collectors.toSet()); // Collect the Flux into a Set

        return companyMono.flatMap(company->
                driversMono.map(drivers -> new CompanyWebModel(company, drivers))
        );
    }



    @Override
    public CompanyWebModel newWM() {
        return new CompanyWebModel();
    }

    @Override
    public CompanyWebModel newWM(CompanyDto dto) {
        return new CompanyWebModel(dto);
    }
}