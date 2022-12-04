package com.exercise.carparking.domain.viewmodel.availability;

import com.exercise.carparking.domain.CarParkAvailabilities;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CarParkAvailabilityResponse {
    @JsonProperty("result")
    List<CarParkAvailabilityResult> carParkAvailabilityResults;

    public static CarParkAvailabilityResponse from(CarParkAvailabilities carParkAvailabilities) {
        return new CarParkAvailabilityResponse(
                carParkAvailabilities.values().stream()
                        .map(carParkAvailability -> new CarParkAvailabilityResult(
                                carParkAvailability.getCarParkInformation().getAddress(),
                                carParkAvailability.getCarParkInformation().getLatitude(),
                                carParkAvailability.getCarParkInformation().getLongitude(),
                                carParkAvailability.getLotType(),
                                carParkAvailability.getTotalLots(),
                                carParkAvailability.getAvailableLots()
                        )).collect(Collectors.toList())
        );
    }
}
