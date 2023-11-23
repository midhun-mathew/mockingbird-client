package com.incredibles;

import org.springframework.http.HttpMethod;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.ExchangeFunction;
import reactor.core.publisher.Mono;

import java.net.URI;

public class WebClientMockingFilter implements ExchangeFilterFunction {

    private final Config config;
    public WebClientMockingFilter(Config config) {
        this.config = config;
    }


    @Override
    public Mono<ClientResponse> filter(ClientRequest request, ExchangeFunction next) {
        if(config.isEnabled()){
            ClientRequest newRequest = ClientRequest.from(request)
                    .url(URI.create(config.getMockServerUrl()))
                    .method(HttpMethod.POST)
                    .header("mb_source_application", config.getApplicationName())
                    .header("mb_destination_url", request.url().toString())
                    .header("mb_method", request.method().toString())
                    .build();

            return next.exchange(newRequest)
                    .onErrorResume(e -> next.exchange(request));
        } else {
            return next.exchange(request);
        }
    }
}