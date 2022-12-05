package com.exercise.carparking.carparkingapi.mapper;

import com.exercise.carparking.carparkingapi.domain.CarParkInformation;
import com.exercise.carparking.carparkingapi.domain.CarParkInformations;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CarParkInformationMapper {
    List<CarParkInformation> findAll();
    CarParkInformation findBy(@Param("carParkNo") String carParkNo);

    void register(@Param("carParkInformations") CarParkInformations carParkInformations);

    void clear();
}
