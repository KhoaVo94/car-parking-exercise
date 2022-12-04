package com.exercise.carparking.controller;

import com.exercise.carparking.domain.CarParkAvailabilities;
import com.exercise.carparking.domain.Pageable;
import com.exercise.carparking.domain.viewmodel.availability.CarParkAvailabilityItems;
import com.exercise.carparking.domain.viewmodel.availability.CarParkAvailabilityResponse;
import com.exercise.carparking.domain.viewmodel.availability.CarParkAvailabilityResult;
import com.exercise.carparking.domain.viewmodel.information.CarParkInformationResponse;
import com.exercise.carparking.service.CarParkAvailabilityService;
import com.exercise.carparking.service.CarParkInformationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping(value = "/v1/carparks")
public class CarParkingController {

    @Autowired
    CarParkInformationService carParkInformationService;
    @Autowired
    CarParkAvailabilityService carParkAvailabilityService;

    @RequestMapping(value = "/information/update", method = RequestMethod.PATCH)
    public Mono<CarParkInformationResponse> updateInformation() {
        return carParkInformationService.updateWithLatestData(2000);
    }

    @RequestMapping(value = "/availability/update", method = RequestMethod.PATCH)
    public Mono<CarParkAvailabilityItems> updateAvailability() {
        return carParkAvailabilityService.updateWithLatestData();
    }

    @RequestMapping(value = "/nearest")
    public ResponseEntity<List<CarParkAvailabilityResult>> getCarParkAvailabilities(
            @RequestParam(name = "latitude") Double latitude,
            @RequestParam(name = "longitude") Double longitude,
            @RequestParam(name = "page", required = false) Integer pageNumber,
            @RequestParam(name = "per_page", required = false) Integer pageSize
    ) {
        Pageable pageable = new Pageable(
                Objects.nonNull(pageNumber) ? pageNumber : -1,
                Objects.nonNull(pageSize) ? pageSize : -1);
        CarParkAvailabilities carParkAvailabilities = carParkAvailabilityService.getCarParkAvailabilities(latitude, longitude, pageable);
        return ResponseEntity.ok(CarParkAvailabilityResponse.from(carParkAvailabilities).getCarParkAvailabilityResults());
    }
}
