package com.exercise.carparking.unittest

import com.exercise.carparking.datasource.repository.CarPArkAvailabilityQueryRepository
import com.exercise.carparking.datasource.repository.CarParkAvailabilityCommandRepository
import com.exercise.carparking.domain.CarParkAvailabilities
import com.exercise.carparking.domain.Pageable
import com.exercise.carparking.domain.viewmodel.availability.CarParkAvailabilityItems
import com.exercise.carparking.service.CarParkAvailabilityService
import org.springframework.web.reactive.function.client.ExchangeStrategies
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono
import spock.lang.Specification

class CarParkAvailabilityEndpointTests extends Specification {
    CarParkAvailabilityService carParkAvailabilityService
    CarParkAvailabilities verifyCarParkAvailabilities

    def setup() {
        def carParkAvailabilityCommandRepository = Mock(CarParkAvailabilityCommandRepository.class)
        def carPArkAvailabilityQueryRepository = Mock(CarPArkAvailabilityQueryRepository.class)
        def carParkAvailabilityWebClient = carParkAvailabilityWebClient();

        carParkAvailabilityCommandRepository.register(_ as CarParkAvailabilities) >> { CarParkAvailabilities carParkAvailabilities ->
            verifyCarParkAvailabilities = carParkAvailabilities
        }

        carParkAvailabilityService = new CarParkAvailabilityService(
                carParkAvailabilityCommandRepository: carParkAvailabilityCommandRepository,
                carPArkAvailabilityQueryRepository: carPArkAvailabilityQueryRepository,
                carParkAvailabilityWebClient: carParkAvailabilityWebClient
        )
    }

    def "callingGetCarPArkAvailabilityAPITest"() {
        when:
        Mono<CarParkAvailabilityItems> carParkAvailabilityItemsMono = carParkAvailabilityService.updateWithLatestData()
        CarParkAvailabilityItems carParkAvailabilityItems = carParkAvailabilityItemsMono.block()

        then:
        !carParkAvailabilityItems.asCarParkAvailabilities().isEmpty()
        Objects.equals(verifyCarParkAvailabilities, carParkAvailabilityItems.asCarParkAvailabilities())

    }

    WebClient carParkAvailabilityWebClient() {
        final int size = 16 * 1024 * 1024;
        final ExchangeStrategies strategies = ExchangeStrategies.builder()
                .codecs(codecs -> codecs.defaultCodecs().maxInMemorySize(size))
                .build();

        return WebClient.builder()
                .exchangeStrategies(strategies)
                .baseUrl("https://api.data.gov.sg/v1/transport/carpark-availability")
                .build();
    }
}
