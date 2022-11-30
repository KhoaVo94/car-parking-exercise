package com.exercise.carparking.domain.viewmodel.availability;

import com.exercise.carparking.domain.CarParkAvailabilities;
import com.exercise.carparking.domain.CarParkAvailability;
import com.exercise.carparking.domain.CarParkInformation;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CarParkAvailabilityJson {
    @JsonProperty("carpark_info")
    List<CarParkAvailabilityInfo> carParkAvailabilityInfos;

    @JsonProperty("carpark_number")
    String carParkNo;

    public CarParkAvailabilities asCarParkAvailability() {
        return new CarParkAvailabilities(
                carParkAvailabilityInfos.stream()
                        .map(info -> new CarParkAvailability(
                                info.getTotalLots(),
                                info.getLotsAvailable(),
                                info.getLotType(),
                                new CarParkInformation(carParkNo,"", 0.0, 0.0)))
                        .collect(Collectors.toList())
        );
    }
}
