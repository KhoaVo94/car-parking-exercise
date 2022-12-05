package com.exercise.carparking.carparkingapi.datasource.repository;

import com.exercise.carparking.carparkingapi.domain.CarParkAvailabilities;
import com.exercise.carparking.carparkingapi.domain.CarParkAvailability;
import com.exercise.carparking.carparkingapi.domain.Pageable;

public interface CarPArkAvailabilityQueryRepository {
    CarParkAvailabilities findAll();
    CarParkAvailability findBy(String carParkNo, String lotType);
    CarParkAvailabilities findByCoordinate(Double longitude, Double latitude, Pageable pageable);
}
