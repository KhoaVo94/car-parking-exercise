package com.exercise.carparking.service;

import com.exercise.carparking.datasource.repository.CarPArkAvailabilityQueryRepository;
import com.exercise.carparking.datasource.repository.CarParkAvailabilityCommandRepository;
import com.exercise.carparking.domain.CarParkAvailabilities;
import com.exercise.carparking.domain.CarParkAvailability;
import com.exercise.carparking.domain.Pageable;
import com.exercise.carparking.domain.viewmodel.availability.CarParkAvailabilityItems;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class CarParkAvailabilityService {

    CarParkAvailabilityCommandRepository carParkAvailabilityCommandRepository;
    CarPArkAvailabilityQueryRepository carPArkAvailabilityQueryRepository;

    WebClient carParkAvailabilityWebClient;

    @Autowired
    public CarParkAvailabilityService(CarParkAvailabilityCommandRepository carParkAvailabilityCommandRepository,
                                      CarPArkAvailabilityQueryRepository carPArkAvailabilityQueryRepository,
                                      WebClient carParkAvailabilityWebClient) {
        this.carParkAvailabilityCommandRepository = carParkAvailabilityCommandRepository;
        this.carPArkAvailabilityQueryRepository = carPArkAvailabilityQueryRepository;
        this.carParkAvailabilityWebClient = carParkAvailabilityWebClient;
    }

    public CarParkAvailabilities findAll() {
        return carPArkAvailabilityQueryRepository.findAll();
    }

    public CarParkAvailability findBy(String carParkNo, String lotType) {
        return carPArkAvailabilityQueryRepository.findBy(carParkNo, lotType);
    }

    public CarParkAvailabilities getCarParkAvailabilities(Double latitude, Double longtitude, Pageable pageable) {
        return carPArkAvailabilityQueryRepository.findByCoordinate(latitude, longtitude, pageable);
    }

    public Mono<CarParkAvailabilityItems> updateWithLatestData() {
        return carParkAvailabilityWebClient.get()
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(CarParkAvailabilityItems.class)
                .map(response -> {
                    CarParkAvailabilities carParkAvailabilities = response.asCarParkAvailabilities();
                    update(carParkAvailabilities);
                    return response;
                });
    }

    @Transactional
    public void update(CarParkAvailabilities carParkAvailabilities) {
        carParkAvailabilityCommandRepository.register(carParkAvailabilities);
    }

    @Transactional
    public void deleteIfNotExist() {
        carParkAvailabilityCommandRepository.deleteIfParkNotExist();
    }
}
