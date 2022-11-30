package com.exercise.carparking.datasource.repository;

import com.exercise.carparking.domain.CarParkAvailabilities;

public interface CarParkAvailabilityCommandRepository {
    void register(CarParkAvailabilities carParkAvailabilities);
    void deleteIfParkNotExist();
}
