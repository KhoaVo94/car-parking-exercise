package com.exercise.carparking.domain.viewmodel.information;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarParkInformationJson {

    @JsonProperty("car_park_no")
    String carParkNo;

    @JsonProperty("x_coord")
    Double xCoord;

    @JsonProperty("y_coord")
    Double yCoord;

    @JsonProperty("address")
    String address;
}
