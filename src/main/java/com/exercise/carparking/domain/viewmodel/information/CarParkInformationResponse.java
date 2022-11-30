package com.exercise.carparking.domain.viewmodel.information;

import com.exercise.carparking.domain.CarParkInformation;
import com.exercise.carparking.domain.CarParkInformations;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarParkInformationResponse {
    @JsonProperty("result")
    CarParkingRecords carParkingRecords;

    public static CarParkInformationResponse from(CarParkInformations carParkInformations) {

        return new CarParkInformationResponse(
                new CarParkingRecords(
                        carParkInformations.values().stream()
                                .map(carParkInformation ->
                                        new CarParkInformationJson(
                                                carParkInformation.getCarParkNo(),
                                                carParkInformation.getLongitude(),
                                                carParkInformation.getLatitude(),
                                                carParkInformation.getAddress()
                                        )
                                )
                                .collect(Collectors.toList())
                )
        );
    }

    public CarParkInformations asCarParkInformations() {
        if (Objects.isNull(carParkingRecords)
                || Objects.isNull(carParkingRecords.carParkInformations)
                || carParkingRecords.carParkInformations.isEmpty()) {
            return CarParkInformations.ofEmpty();
        }

        return new CarParkInformations(
                carParkingRecords.carParkInformations.stream()
                        .map(json -> new CarParkInformation(
                                json.getCarParkNo(),
                                json.getAddress(),
                                json.getYCoord(),
                                json.getXCoord()
                        )).collect(Collectors.toList())
        );
    }
}
