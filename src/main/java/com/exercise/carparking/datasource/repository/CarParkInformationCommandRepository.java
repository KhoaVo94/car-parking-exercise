package com.exercise.carparking.datasource.repository;

import com.exercise.carparking.domain.CarParkInformations;

public interface CarParkInformationCommandRepository {
    void register(CarParkInformations carParkInformations);
    void clear();
}
