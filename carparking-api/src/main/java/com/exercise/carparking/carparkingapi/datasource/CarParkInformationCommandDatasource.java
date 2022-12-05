package com.exercise.carparking.carparkingapi.datasource;

import com.exercise.carparking.carparkingapi.datasource.repository.CarParkInformationCommandRepository;
import com.exercise.carparking.carparkingapi.domain.CarParkInformations;
import com.exercise.carparking.carparkingapi.mapper.CarParkInformationMapper;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

@Repository
public class CarParkInformationCommandDatasource implements CarParkInformationCommandRepository {

    CarParkInformationMapper carParkInformationMapper;

    @Autowired
    public CarParkInformationCommandDatasource(@Qualifier("registerSessionTemplate") SqlSessionTemplate registerSession) {
        carParkInformationMapper = registerSession.getMapper(CarParkInformationMapper.class);
    }

    @Override
    public void register(CarParkInformations carParkInformations) {
        carParkInformationMapper.register(carParkInformations);
    }

    @Override
    public void clear() {
        carParkInformationMapper.clear();
    }
}
