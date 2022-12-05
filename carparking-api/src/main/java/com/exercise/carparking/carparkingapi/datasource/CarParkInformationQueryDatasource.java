package com.exercise.carparking.carparkingapi.datasource;

import com.exercise.carparking.carparkingapi.datasource.repository.CarParkInformationQueryRepository;
import com.exercise.carparking.carparkingapi.domain.CarParkInformation;
import com.exercise.carparking.carparkingapi.domain.CarParkInformations;
import com.exercise.carparking.carparkingapi.mapper.CarParkInformationMapper;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

@Repository
public class CarParkInformationQueryDatasource implements CarParkInformationQueryRepository {

    CarParkInformationMapper carParkInformationMapper;

    @Autowired
    public CarParkInformationQueryDatasource(@Qualifier("querySessionTemplate") SqlSessionTemplate querySession) {
        carParkInformationMapper = querySession.getMapper(CarParkInformationMapper.class);
    }

    @Override
    public CarParkInformations findAll() {
        return new CarParkInformations(carParkInformationMapper.findAll());
    }

    @Override
    public CarParkInformation findBy(String carParkNo) {
        return carParkInformationMapper.findBy(carParkNo);
    }
}
