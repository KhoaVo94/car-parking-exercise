package com.exercise.carparking.domain.viewmodel.availability;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CarParkAvailabilityInfo {
    @JsonProperty("total_lots")
    Integer totalLots;

    @JsonProperty("lot_type")
    String lotType;

    @JsonProperty("lots_available")
    Integer lotsAvailable;

}
