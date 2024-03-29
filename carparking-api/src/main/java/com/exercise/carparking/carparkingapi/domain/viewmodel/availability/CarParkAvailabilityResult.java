package com.exercise.carparking.carparkingapi.domain.viewmodel.availability;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CarParkAvailabilityResult {
    @JsonProperty("address")
    String address;

    @JsonProperty("latitude")
    Double latitude;

    @JsonProperty("longitude")
    Double longitude;

    @JsonProperty("lot_type")
    String lotType;

    @JsonProperty("total_lots")
    Integer totalLots;

    @JsonProperty("available_lots")
    Integer availabltLots;
}
