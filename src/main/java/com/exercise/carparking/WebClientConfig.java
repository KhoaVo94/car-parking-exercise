package com.exercise.carparking;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Slf4j
@Configuration
public class WebClientConfig {

    @Value("${carpark.info.api.url}")
    String infoUrl;
    @Value("${carpark.availability.api.url}")
    String availabilityUrl;

    @Bean
    WebClient.Builder baseWebClientBuilder() {
        final int size = 16 * 1024 * 1024;
        final ExchangeStrategies strategies = ExchangeStrategies.builder()
                .codecs(codecs -> codecs.defaultCodecs().maxInMemorySize(size))
                .build();

        return WebClient.builder()
                .exchangeStrategies(strategies)
                .filters(exchangeFilterFunctions -> {
                    exchangeFilterFunctions.add(logRequest());
                    exchangeFilterFunctions.add(logResponse());
                });
    }

    @Bean
    WebClient carParkInformationWebClient(WebClient.Builder baseWebClientBuilder) {
        return baseWebClientBuilder
                .baseUrl(infoUrl)
                .build();
    }

    @Bean
    WebClient carParkAvailabilityWebClient(WebClient.Builder baseWebClientBuilder) {
        return baseWebClientBuilder()
                .baseUrl(availabilityUrl)
                .build();
    }

    ExchangeFilterFunction logRequest() {
        return ExchangeFilterFunction.ofRequestProcessor(clientRequest -> {
            StringBuilder sb = new StringBuilder("Request: \n");
            log.debug(sb.toString());
            return Mono.just(clientRequest);
        });
    }

    ExchangeFilterFunction logResponse() {
        return ExchangeFilterFunction.ofResponseProcessor(clientResponse -> {
            StringBuilder sb = new StringBuilder("Response: \n");
            log.debug(sb.toString());
            return Mono.just(clientResponse);
        });
    }
}
