package com.exercise.carparking.carparkingapi.mapper;

import com.exercise.carparking.carparkingapi.domain.CarParkAvailabilities;
import com.exercise.carparking.carparkingapi.domain.CarParkAvailability;
import com.exercise.carparking.carparkingapi.domain.Pageable;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CarParkAvailabilityMapper {
    List<CarParkAvailability> findAll();
    CarParkAvailability findBy(@Param("carParkNo") String carParkNo, @Param("lotType") String lotType);
    List<CarParkAvailability> findByCoordinate(@Param("longitude") Double longitude, @Param("latitude") Double latitude, @Param("pageable") Pageable pageable);

    void register(@Param("carParkAvailabilities") CarParkAvailabilities carParkAvailabilities);
    void deleteIfParkNotExist();
}
