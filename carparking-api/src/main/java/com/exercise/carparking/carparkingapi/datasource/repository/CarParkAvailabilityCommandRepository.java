package com.exercise.carparking.carparkingapi.datasource.repository;

import com.exercise.carparking.carparkingapi.domain.CarParkAvailabilities;

public interface CarParkAvailabilityCommandRepository {
    void register(CarParkAvailabilities carParkAvailabilities);
    void deleteIfParkNotExist();
}
