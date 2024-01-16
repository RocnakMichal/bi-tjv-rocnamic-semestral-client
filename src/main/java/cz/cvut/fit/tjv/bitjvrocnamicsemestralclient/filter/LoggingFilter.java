package cz.cvut.fit.tjv.bitjvrocnamicsemestralclient.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.ExchangeFunction;
import reactor.core.publisher.Mono;
import reactor.util.annotation.NonNull;

public class LoggingFilter implements ExchangeFilterFunction {

    private final Logger logger = LoggerFactory.getLogger(LoggingFilter.class);

    @Override
    @NonNull
    public Mono<ClientResponse> filter(ClientRequest request, ExchangeFunction next) {
        logRequest(request);
        return next.exchange(request);
    }

    private void logRequest(ClientRequest request) {
        HttpMethod method = request.method();
        String url = request.url().toString();
        HttpHeaders headers = request.headers();

        logger.info("Request: {} {}", method, url);
        logger.info("Headers: {}", headers);
    }
}
