package com.exercise.carparking.unittest

import com.exercise.carparking.carparkingapi.datasource.repository.CarParkInformationCommandRepository
import com.exercise.carparking.carparkingapi.datasource.repository.CarParkInformationQueryRepository
import com.exercise.carparking.carparkingapi.domain.CarParkInformations
import com.exercise.carparking.carparkingapi.domain.viewmodel.information.CarParkInformationResponse
import com.exercise.carparking.carparkingapi.service.CarParkInformationService
import com.exercise.carparking.carparkingapi.transaction.Executor
import com.exercise.carparking.carparkingapi.transaction.TransactionExecutor
import org.springframework.web.reactive.function.client.ExchangeStrategies
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono
import spock.lang.Specification
import spock.lang.Unroll

class CarParkInformationEndpointTests extends Specification {

    CarParkInformationService carParkInformationService
    CarParkInformations verifyCarParkInformations

    def setup() {
        def carParkInformationQueryRepository = Mock(CarParkInformationQueryRepository.class)
        def carParkInformationCommandRepository = Mock(CarParkInformationCommandRepository.class)
        def carParkInformationWebClient = carParkInformationWebClient()
        def transactionExecutor = Mock(TransactionExecutor.class)

        transactionExecutor.execute(_ as Executor) >> { Executor executor ->
            executor.execute()
        }

        carParkInformationCommandRepository.register(_ as CarParkInformations) >> { CarParkInformations carParkInformations ->
            verifyCarParkInformations = carParkInformations
        }

        carParkInformationService = new CarParkInformationService(
                carParkInformationQueryRepository: carParkInformationQueryRepository,
                carParkInformationCommandRepository: carParkInformationCommandRepository,
                carParkInformationWebClient: carParkInformationWebClient,
                transactionExecutor: transactionExecutor,
                carParkInfoResource: "139a3035-e624-4f56-b63f-89ae28d4ae4c"
        )
    }

    @Unroll
    def "callingGetCarParkInformationAPITest"() {
        when:
        Mono<CarParkInformationResponse> carParkInformationResponseMono = carParkInformationService.updateWithLatestData(2500)
        CarParkInformationResponse carParkInformationResponse = carParkInformationResponseMono.block()

        then:
        !carParkInformationResponse.asCarParkInformations().isEmpty()
        carParkInformationResponse.carParkingRecords.total == carParkInformationResponse.asCarParkInformations().values().size()
        Objects.equals(verifyCarParkInformations, carParkInformationResponse.asCarParkInformations())
    }

    WebClient carParkInformationWebClient() {
        final int size = 16 * 1024 * 1024;
        final ExchangeStrategies strategies = ExchangeStrategies.builder()
                .codecs(codecs -> codecs.defaultCodecs().maxInMemorySize(size))
                .build();

        return WebClient.builder()
                .exchangeStrategies(strategies)
                .baseUrl("https://data.gov.sg/api/action/datastore_search")
                .build();
    }
}
