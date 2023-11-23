package com.incredibles;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.ExchangeFunction;
import reactor.core.publisher.Mono;

import java.net.URI;

@Component
public class WebClientMockingFilter implements ExchangeFilterFunction {

    @Autowired
    private Environment env;

    @Value("${mockingbird.enabled:false}")
    boolean mockingBirdEnabled;

    @Value("${spring.application.name}")
    String applicationName;

    private final Config config;
    public WebClientMockingFilter(Config config) {
        this.config = config;
    }


    @Override
    public Mono<ClientResponse> filter(ClientRequest request, ExchangeFunction next) {
        if(mockingBirdEnabled){
            ClientRequest newRequest = ClientRequest.from(request).url(URI.create(config.getMockServerUrl()))
                    .header("mb_source_application", applicationName)
                    .header("mb_destination_url", request.url().toString())
                    .build();

            return next.exchange(newRequest)
                    .onErrorResume(e -> next.exchange(request));
        } else {
            return next.exchange(request);
        }
    }
}