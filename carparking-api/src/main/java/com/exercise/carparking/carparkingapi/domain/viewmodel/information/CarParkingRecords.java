package com.exercise.carparking.carparkingapi.domain.viewmodel.information;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collections;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarParkingRecords {
    @JsonProperty("records")
    List<CarParkInformationJson> carParkInformations;

    @JsonProperty("total")
    Integer total;

    public List<CarParkInformationJson> carParkInformations() {
        return Collections.unmodifiableList(carParkInformations);
    }
}
