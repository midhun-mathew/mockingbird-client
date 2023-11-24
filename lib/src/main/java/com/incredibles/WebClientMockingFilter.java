package com.incredibles;

import org.springframework.http.HttpMethod;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.ExchangeFunction;
import reactor.core.publisher.Mono;

import java.net.URI;

public class WebClientMockingFilter implements ExchangeFilterFunction {

    private final MockingbirdConfig mockingbirdConfig;
    public WebClientMockingFilter(MockingbirdConfig mockingbirdConfig) {
        this.mockingbirdConfig = mockingbirdConfig;
    }


    @Override
    public Mono<ClientResponse> filter(ClientRequest request, ExchangeFunction next) {
        if(mockingbirdConfig.isEnabled()){
            ClientRequest newRequest = ClientRequest.from(request)
                    .url(URI.create(mockingbirdConfig.getMockServerUrl()))
                    .method(HttpMethod.POST)
                    .header("Accept", "application/json")
                    .header("mb_source_application", mockingbirdConfig.getApplicationName())
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