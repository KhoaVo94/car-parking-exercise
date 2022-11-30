package com.exercise.carparking.datasource;

import com.exercise.carparking.datasource.repository.CarParkInformationQueryRepository;
import com.exercise.carparking.domain.CarParkInformation;
import com.exercise.carparking.domain.CarParkInformations;
import com.exercise.carparking.mapper.CarParkInformationMapper;
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
