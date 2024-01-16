package cz.cvut.fit.tjv.bitjvrocnamicsemestralclient.ui;



import cz.cvut.fit.tjv.bitjvrocnamicsemestralclient.data.CarClient;
import cz.cvut.fit.tjv.bitjvrocnamicsemestralclient.data.DriverClient;
import cz.cvut.fit.tjv.bitjvrocnamicsemestralclient.model.CarDto;
import cz.cvut.fit.tjv.bitjvrocnamicsemestralclient.model.CarWebModel;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import reactor.core.publisher.Mono;

@Controller
@RequestMapping("/car")
public class CarWebController extends AbstractWebController<CarWebModel, CarDto, CarClient, Long> {
    DriverClient driverClient;

    public CarWebController(DriverClient driverClient, CarClient client) {
        super(client, "car");
        this.driverClient = driverClient;
    }




    @Override
    public Mono<String> showCreate(Model model) {
        return driverClient.list()
                .collectList()
                .doOnNext(driverWebModels -> model.addAttribute("drivers", driverWebModels))
                .then(super.showCreate(model))
                .onErrorResume(throwable -> Mono.just("redirect:/error/" + getStatusCode(throwable)))
                ;
    }

    @Override
    @GetMapping("/{id}/edit")
    public Mono<String> showEdit(@PathVariable Long id, Model model) {
        return driverClient.list()
                .collectList()
                .doOnNext(driverWebModels -> model.addAttribute("drivers", driverWebModels))
                .then(super.showEdit(id, model))
                .onErrorResume(throwable -> Mono.just("redirect:/error/" + getStatusCode(throwable)))
                ;
    }
}

