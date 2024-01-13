package cz.cvut.fit.tjv.bitjvrocnamicsemestralclient.data;

import cz.cvut.fit.tjv.bitjvrocnamicsemestralclient.model.CarWebModel;
import cz.cvut.fit.tjv.bitjvrocnamicsemestralclient.model.CarDto;

import cz.cvut.fit.tjv.bitjvrocnamicsemestralclient.model.DriverDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class CarClient extends AbstractClient<CarWebModel,CarDto, Long> {
    protected static final String DEP_URI = ONE_URI + "/driver";
    protected final WebClient driverWebClient;

    public CarClient (@Value("${backend_url}") String backendUrl) {
        super(backendUrl, "/car", CarWebModel.class);
        driverWebClient = WebClient.create(backendUrl + "/driver");;
    }

    @Override
    public Mono<CarWebModel> create(CarWebModel wm) {
        return driverWebClient.post() // HTTP POST
                .uri("/{drvId}/car", wm.driverId)
                .contentType(MediaType.APPLICATION_JSON) // set HTTP header
                .bodyValue(wm) // POST data
                .retrieve() // request specification finished
                .bodyToMono(CarWebModel.class) // interpret response body as one element using WM class
                ;
    }

    @Override
    public Mono<Void> edit(Long id, CarWebModel carWebModel) {
        return webClient.put() // HTTP PUT
                .uri(DEP_URI + "/{FFId}", id, carWebModel.driverId)
                .retrieve() // request specification finished
                .toBodilessEntity()
                .then(Mono.defer(() -> super.edit(id, carWebModel)))
                ;
    }

    @Override
    protected Mono<CarWebModel> readById(Long id) {
        return super.readById(id).flatMap(this::readDriver);
    }

    @Override
    protected Flux<CarWebModel> readAll() {
        return super.readAll().flatMap(this::readDriver);
    }



    private Mono<CarWebModel> readDriver(CarWebModel carWebModel) {
        return webClient.get() // /object
                .uri(DEP_URI, carWebModel.id) // /object/{id}
                .retrieve() // read
                .bodyToMono(DriverDto.class) // interpret response body as WM using WM class
                .map(driverDto -> {
                    carWebModel.setDriver(driverDto);
                    return carWebModel;
                })
                ;
    }


    @Override
    public CarWebModel newWM() {
        return new CarWebModel();
    }

    @Override
    public CarWebModel newWM(CarDto dto) {
        return new CarWebModel(dto);
    }
}
