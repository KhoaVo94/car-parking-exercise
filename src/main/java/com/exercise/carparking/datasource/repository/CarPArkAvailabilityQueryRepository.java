package com.exercise.carparking.datasource.repository;

import com.exercise.carparking.domain.CarParkAvailabilities;
import com.exercise.carparking.domain.CarParkAvailability;
import com.exercise.carparking.domain.Pageable;

public interface CarPArkAvailabilityQueryRepository {
    CarParkAvailabilities findAll();
    CarParkAvailability findBy(String carParkNo, String lotType);
    CarParkAvailabilities findByCoordinate(Double longitude, Double latitude, Pageable pageable);
}
