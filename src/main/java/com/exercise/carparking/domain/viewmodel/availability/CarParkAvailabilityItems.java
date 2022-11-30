package com.exercise.carparking.domain.viewmodel.availability;

import com.exercise.carparking.domain.CarParkAvailabilities;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CarParkAvailabilityItems {
    @JsonProperty("items")
    List<CarParkAvailabilityData> carParkAvailabilityData;

    public CarParkAvailabilities asCarParkAvailabilities() {
        if (Objects.isNull(carParkAvailabilityData) || carParkAvailabilityData.isEmpty()) {
            return CarParkAvailabilities.ofEmpty();
        }

        return new CarParkAvailabilities(
                carParkAvailabilityData.stream()
                        .flatMap(data -> data.asCarParkAvailability().values().stream())
                        .collect(Collectors.toList())
        );
    }
}
