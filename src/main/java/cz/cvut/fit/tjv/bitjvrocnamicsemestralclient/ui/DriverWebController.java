package cz.cvut.fit.tjv.bitjvrocnamicsemestralclient.ui;

import cz.cvut.fit.tjv.bitjvrocnamicsemestralclient.data.CompanyClient;
import cz.cvut.fit.tjv.bitjvrocnamicsemestralclient.data.DriverClient;
import cz.cvut.fit.tjv.bitjvrocnamicsemestralclient.model.DriverWebModel;
import cz.cvut.fit.tjv.bitjvrocnamicsemestralclient.model.DriverDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.Collection;
import java.util.NoSuchElementException;


@Controller
@RequestMapping("/driver")

public class DriverWebController extends AbstractWebController<DriverWebModel, DriverDto, DriverClient,Long> {
    CompanyClient companyClient;
    DriverClient driverClient;

    public DriverWebController(DriverClient driverClient, CompanyClient companyClient) {
        super(driverClient, "driver");
        this.companyClient=companyClient;
        this.driverClient=driverClient;
    }

    @GetMapping("/deleteWithoutCar")
    public String deleteDriversWithoutCar() {
        try {
            driverClient.deleteDriversWithoutCars();
            return "redirect:/driver";
        } catch (Exception e) {
            return "redirect:/error";
        }
    }

    @Override
    public Mono<String> showCreate(Model model) {
        return companyClient.list()
                .collectList()
                .doOnNext(companyWebModels -> model.addAttribute("companys", companyWebModels))
                .then(super.showCreate(model))
                .onErrorResume(throwable -> Mono.just("redirect:/error/" + getStatusCode(throwable)))
                ;
    }


}

