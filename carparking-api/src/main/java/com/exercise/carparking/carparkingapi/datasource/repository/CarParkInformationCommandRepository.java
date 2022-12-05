package com.exercise.carparking.carparkingapi.datasource.repository;

import com.exercise.carparking.carparkingapi.domain.CarParkInformations;

public interface CarParkInformationCommandRepository {
    void register(CarParkInformations carParkInformations);
    void clear();
}
