package com.exercise.carparking.datasource.repository;

import com.exercise.carparking.domain.CarParkInformation;
import com.exercise.carparking.domain.CarParkInformations;

public interface CarParkInformationQueryRepository {
    CarParkInformations findAll();
    CarParkInformation findBy(String carParkNo);
}
