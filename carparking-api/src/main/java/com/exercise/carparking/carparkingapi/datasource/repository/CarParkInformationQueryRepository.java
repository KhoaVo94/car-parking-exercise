package com.exercise.carparking.carparkingapi.datasource.repository;

import com.exercise.carparking.carparkingapi.domain.CarParkInformation;
import com.exercise.carparking.carparkingapi.domain.CarParkInformations;

public interface CarParkInformationQueryRepository {
    CarParkInformations findAll();
    CarParkInformation findBy(String carParkNo);
}
