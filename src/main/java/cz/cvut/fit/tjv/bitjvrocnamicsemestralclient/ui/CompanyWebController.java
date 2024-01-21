package cz.cvut.fit.tjv.bitjvrocnamicsemestralclient.ui;

import cz.cvut.fit.tjv.bitjvrocnamicsemestralclient.data.CompanyClient;
import cz.cvut.fit.tjv.bitjvrocnamicsemestralclient.data.DriverClient;
import cz.cvut.fit.tjv.bitjvrocnamicsemestralclient.model.CompanyDto;
import cz.cvut.fit.tjv.bitjvrocnamicsemestralclient.model.CompanyWebModel;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;

@Controller
@RequestMapping("/company")
public class CompanyWebController extends AbstractWebController<CompanyWebModel, CompanyDto, CompanyClient,  Long> {

   DriverClient driverClient;
    CompanyClient companyClient;

    public CompanyWebController(CompanyClient companyClient,DriverClient driverClient) {
        super(companyClient, "company");
        this.driverClient=driverClient;
    }








    @GetMapping("/{id}/attend")
    public Mono<String> showAttend(@PathVariable Long id, Model model) {
        return client.showAttend(id)
                .flatMap(companyWebModel -> {
                    model.addAttribute(url, companyWebModel);
                    return driverClient.list()
                            .collectList()
                            .doOnNext(drivers -> model.addAttribute("drivers", drivers))
                            .then(Mono.just(url + "/attend"));
                })
                .onErrorResume(throwable -> Mono.just("redirect:/error/" + getStatusCode(throwable)));
    }

    @PostMapping("/{id}/attend")
    public Mono<String> attend(@PathVariable Long id, @ModelAttribute CompanyWebModel wm) {
        return client.attend(id, wm.driverIds)
                .then(Mono.just("redirect:/" + url + "/" + id))
                .onErrorResume(throwable -> Mono.just("redirect:/error/" + getStatusCode(throwable)))
                ;
    }

   /* @GetMapping("/driver/create")
    public String showCreationForm(Model model) {
        companyClient.list()
                .collectList()
                .subscribe(companys -> model.addAttribute("companys", companys));

        return "driver/create";
    }*/


}
