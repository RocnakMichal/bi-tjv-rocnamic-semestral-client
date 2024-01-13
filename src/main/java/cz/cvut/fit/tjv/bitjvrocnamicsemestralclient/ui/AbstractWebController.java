package cz.cvut.fit.tjv.bitjvrocnamicsemestralclient.ui;

import cz.cvut.fit.tjv.bitjvrocnamicsemestralclient.model.AbstractDtoWithId;
import cz.cvut.fit.tjv.bitjvrocnamicsemestralclient.data.AbstractClient;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

public abstract class AbstractWebController<WM extends DTO, DTO extends AbstractDtoWithId<ID>, CLIENT extends AbstractClient<WM, DTO, ID>, ID> {
    protected final CLIENT client;
    protected final String url;

    public AbstractWebController(CLIENT client, String url) {
        this.client = client;
        this.url = url;
    }

    @GetMapping
    public Mono<String> list(Model model) {
        return client.list()
                .collectList()
                .flatMap(list -> {
                    model.addAttribute(url + "s", list);
                    return Mono.just(url + "/list");
                })
                // only 500 error to handle
                .onErrorResume(throwable -> Mono.just("redirect:/error/" + getStatusCode(throwable)))
                ;
    }

    @GetMapping("/{id}")
    public Mono<String> detail(@PathVariable ID id, Model model) {
        return client.detail(id)
                .doOnNext(detail -> model.addAttribute(url, detail))
                .then(Mono.just(url + "/detail"))
                .onErrorResume(throwable -> Mono.just("redirect:/error/" + getStatusCode(throwable)))
                ;
    }

    @GetMapping("/create")
    public Mono<String> showCreate(Model model) {
        return client.showCreate()
                .doOnNext(wm -> model.addAttribute(url, wm))
                .then(Mono.just(url + "/create"))
                .onErrorResume(throwable -> Mono.just("redirect:/error/" + getStatusCode(throwable)))
                ;
    }

    @PostMapping("/create")
    public Mono<String> create(@ModelAttribute WM wm) {
        return client.create(wm)
                .map(createdWM -> "redirect:/" + url + "/" + createdWM.id)
                .onErrorResume(throwable -> Mono.just("redirect:/error/" + getStatusCode(throwable)))
                ;
    }

    // postMapping because delete doesnt work
    @PostMapping("/{id}")
    public Mono<String> delete(@PathVariable ID id) {
        return client.delete(id)
                .then(Mono.just("redirect:/" + url))
                .onErrorResume(throwable -> Mono.just("redirect:/error/" + getStatusCode(throwable)))
                ;
    }

    @GetMapping("/{id}/edit")
    public Mono<String> showEdit(@PathVariable ID id, Model model) {
        return client.showEdit(id)
                .doOnNext(wm -> model.addAttribute(url, wm))
                .then(Mono.just(url + "/edit"))
                .onErrorResume(throwable -> Mono.just("redirect:/error/" + getStatusCode(throwable)))
                ;
    }

    @PostMapping("/{id}/edit")
    public Mono<String> edit(@PathVariable ID id, @ModelAttribute WM wm, Model model) {
        return client.edit(id, wm)
                .then(Mono.just("redirect:/" + url + "/{id}"))
                .onErrorResume(throwable -> Mono.just("redirect:/error/" + getStatusCode(throwable)))
                ;
    }

    protected int getStatusCode(Throwable throwable) {
        if (throwable instanceof WebClientResponseException)
            return ((WebClientResponseException) throwable).getStatusCode().value();
        return HttpStatus.INTERNAL_SERVER_ERROR.value();
    }
}

