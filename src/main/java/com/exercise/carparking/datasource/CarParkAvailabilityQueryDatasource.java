package com.exercise.carparking.datasource;

import com.exercise.carparking.datasource.repository.CarPArkAvailabilityQueryRepository;
import com.exercise.carparking.domain.CarParkAvailabilities;
import com.exercise.carparking.domain.CarParkAvailability;
import com.exercise.carparking.domain.Pageable;
import com.exercise.carparking.mapper.CarParkAvailabilityMapper;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

@Repository
public class CarParkAvailabilityQueryDatasource implements CarPArkAvailabilityQueryRepository {

    CarParkAvailabilityMapper carParkAvailabilityMapper;

    @Autowired
    public CarParkAvailabilityQueryDatasource(@Qualifier("querySessionTemplate") SqlSessionTemplate querySession) {
        carParkAvailabilityMapper = querySession.getMapper(CarParkAvailabilityMapper.class);
    }

    @Override
    public CarParkAvailabilities findAll() {
        return new CarParkAvailabilities(carParkAvailabilityMapper.findAll());
    }

    @Override
    public CarParkAvailability findBy(String carParkNo, String lotType) {
        return carParkAvailabilityMapper.findBy(carParkNo, lotType);
    }

    @Override
    public CarParkAvailabilities findByCoordinate(Double longitude, Double latitude, Pageable pageable) {
        return new CarParkAvailabilities(carParkAvailabilityMapper.findByCoordinate(longitude, longitude, pageable));
    }
}
