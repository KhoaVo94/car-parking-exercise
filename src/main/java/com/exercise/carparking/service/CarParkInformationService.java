package com.exercise.carparking.service;

import com.exercise.carparking.datasource.repository.CarParkInformationCommandRepository;
import com.exercise.carparking.datasource.repository.CarParkInformationQueryRepository;
import com.exercise.carparking.domain.CarParkInformation;
import com.exercise.carparking.domain.CarParkInformations;
import com.exercise.carparking.domain.viewmodel.information.CarParkInformationResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public class CarParkInformationService {

    CarParkInformationQueryRepository carParkInformationQueryRepository;
    CarParkInformationCommandRepository carParkInformationCommandRepository;
    WebClient carParkInformationWebClient;

    @Value("${carpark.info.resource}")
    String carParkInfoResource;

    @Autowired
    public CarParkInformationService(CarParkInformationQueryRepository carParkInformationQueryRepository,
                                     CarParkInformationCommandRepository carParkInformationCommandRepository,
                                     WebClient carParkInformationWebClient) {
        this.carParkInformationCommandRepository = carParkInformationCommandRepository;
        this.carParkInformationQueryRepository = carParkInformationQueryRepository;
        this.carParkInformationWebClient = carParkInformationWebClient;
    }

    public CarParkInformations findAll() {
        return carParkInformationQueryRepository.findAll();
    }

    public CarParkInformation findBy(String carParkNo) {
        return carParkInformationQueryRepository.findBy(carParkNo);
    }

    public Mono<CarParkInformationResponse> getLatestData() {
        return carParkInformationWebClient.get()
                .uri(uriBuilder -> uriBuilder
                        .queryParam("resource_id", carParkInfoResource)
                        .build())
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(CarParkInformationResponse.class);
    }

    @Transactional
    public void update(CarParkInformations carParkInformations) {
        carParkInformationCommandRepository.clear();
        carParkInformationCommandRepository.register(carParkInformations);
    }
}
