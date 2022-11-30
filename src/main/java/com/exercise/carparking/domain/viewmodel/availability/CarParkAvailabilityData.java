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
public class CarParkAvailabilityData {
    @JsonProperty("carpark_data")
    List<CarParkAvailabilityJson> carParkAvailabilityJsons;

    public CarParkAvailabilities asCarParkAvailability() {
        return new CarParkAvailabilities(
                carParkAvailabilityJsons.stream()
                        .flatMap(json -> json.asCarParkAvailability().values().stream())
                        .collect(Collectors.toList())
        );
    }
}
