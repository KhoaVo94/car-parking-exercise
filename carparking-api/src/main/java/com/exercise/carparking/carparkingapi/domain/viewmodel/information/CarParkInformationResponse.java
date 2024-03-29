package com.exercise.carparking.carparkingapi.domain.viewmodel.information;

import com.exercise.carparking.carparkingapi.domain.CarParkInformation;
import com.exercise.carparking.carparkingapi.domain.CarParkInformations;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarParkInformationResponse {
    @JsonProperty("result")
    CarParkingRecords carParkingRecords;

    public static CarParkInformationResponse from(CarParkInformations carParkInformations) {

        List<CarParkInformationJson> carParkInformationJsons = carParkInformations.values().stream()
                .map(carParkInformation ->
                        new CarParkInformationJson(
                                carParkInformation.getCarParkNo(),
                                carParkInformation.getLongitude(),
                                carParkInformation.getLatitude(),
                                carParkInformation.getAddress()
                        )
                )
                .collect(Collectors.toList());

        return new CarParkInformationResponse(
                new CarParkingRecords(carParkInformationJsons,carParkInformationJsons.size())
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
