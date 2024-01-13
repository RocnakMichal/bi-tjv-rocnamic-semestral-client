package cz.cvut.fit.tjv.bitjvrocnamicsemestralclient.data;

import cz.cvut.fit.tjv.bitjvrocnamicsemestralclient.model.AbstractDtoWithId;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public abstract class AbstractClient<WM extends DTO, DTO extends AbstractDtoWithId<ID>, ID> {
    protected static final String ONE_URI = "/{id}";
    protected final WebClient webClient;
    protected final Class<WM> wmClass;

    public AbstractClient(String backendUrl, String url, Class<WM> wmClass) {
        webClient = WebClient.builder()
                .baseUrl(backendUrl + url)
                .build();
        this.wmClass = wmClass;
    }

    public Mono<WM> detail(ID id) {
        return readById(id);
    }

    public Flux<WM> list() {
        return readAll();
    }

    public Mono<WM> showCreate() {
        return Mono.just(newWM());
    }

    public Mono<WM> create(WM wm) {
        return webClient.post() // HTTP POST
                .contentType(MediaType.APPLICATION_JSON) // set HTTP header
                .bodyValue(wm) // POST data
                .retrieve() // request specification finished
                .bodyToMono(wmClass) // interpret response body as one element using WM class
                ;
    }

    public Mono<WM> showEdit(ID id) {
        return readById(id);
    }

    public Mono<Void> edit(ID id, WM wm) {
        return webClient.put() // HTTP PUT
                .uri(ONE_URI, id) // URI /{id}
                .contentType(MediaType.APPLICATION_JSON) // HTTP header
                .bodyValue(wm) // HTTP body
                .retrieve()
                .bodyToMono(Void.class)
                ;
    }

    public Mono<Void> delete(ID id) {
        return webClient.delete() // HTTP DELETE
                .uri(ONE_URI, id) // URI
                .retrieve() // request specification finished
                .toBodilessEntity()
                .then()
                ;
    }

    protected Flux<WM> readAll() {
        return webClient.get() // HTTP GET
                .retrieve() // request specification finished
                .bodyToFlux(wmClass) // interpret response body as a collection using WM class
                .map(this::newWM)
                ;
    }

    protected Mono<WM> readById(ID id) {
        return webClient.get() // /object
                .uri(ONE_URI, id) // /object/{id}
                .retrieve() // read
                .bodyToMono(wmClass) // interpret response body as WM using WM class
                ;
    }

    public abstract WM newWM();

    public abstract WM newWM(DTO dto);
}
