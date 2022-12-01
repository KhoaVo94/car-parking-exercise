package com.exercise.carparking;

import io.netty.handler.logging.LogLevel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ClientHttpConnector;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;
import reactor.netty.transport.logging.AdvancedByteBufFormat;

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

        HttpClient httpClient = HttpClient.create()
                .wiretap(this.getClass().getCanonicalName(), LogLevel.DEBUG, AdvancedByteBufFormat.TEXTUAL);
        ClientHttpConnector conn = new ReactorClientHttpConnector(httpClient);

        return WebClient.builder()
                .exchangeStrategies(strategies)
                .clientConnector(conn)
                .filters(exchangeFilterFunctions ->
                    exchangeFilterFunctions.add(logRequest())
                );
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
            log.debug(String.format("Request: %s %s", clientRequest.method(), clientRequest.url()));
            clientRequest
                    .headers()
                    .forEach((name, values) -> values.forEach(value -> log.debug(String.format("%s: %s", name, value))));

            return Mono.just(clientRequest);
        });
    }
}
