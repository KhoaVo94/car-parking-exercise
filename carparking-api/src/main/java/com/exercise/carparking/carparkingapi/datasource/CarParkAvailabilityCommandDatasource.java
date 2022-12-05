package com.exercise.carparking.carparkingapi.datasource;

import com.exercise.carparking.carparkingapi.datasource.repository.CarParkAvailabilityCommandRepository;
import com.exercise.carparking.carparkingapi.domain.CarParkAvailabilities;
import com.exercise.carparking.carparkingapi.mapper.CarParkAvailabilityMapper;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

@Repository
public class CarParkAvailabilityCommandDatasource implements CarParkAvailabilityCommandRepository {

    CarParkAvailabilityMapper carParkAvailabilityMapper;

    @Autowired
    public CarParkAvailabilityCommandDatasource(@Qualifier("registerSessionTemplate") SqlSessionTemplate registerSession) {
        carParkAvailabilityMapper = registerSession.getMapper(CarParkAvailabilityMapper.class);
    }

    @Override
    public void register(CarParkAvailabilities carParkAvailabilities) {
        carParkAvailabilityMapper.register(carParkAvailabilities);
    }

    @Override
    public void deleteIfParkNotExist() {
        carParkAvailabilityMapper.deleteIfParkNotExist();
    }
}
