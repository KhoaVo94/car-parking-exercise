package com.exercise.carparking.datasource;

import com.exercise.carparking.datasource.repository.CarParkAvailabilityCommandRepository;
import com.exercise.carparking.domain.CarParkAvailabilities;
import com.exercise.carparking.mapper.CarParkAvailabilityMapper;
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
