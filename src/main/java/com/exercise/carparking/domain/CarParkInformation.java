package com.exercise.carparking.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

import java.util.Objects;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CarParkInformation {
    String carParkNo;
    String address;
    Double latitude;
    Double longitude;

    public static CarParkInformation ofEmpty() {
        return new CarParkInformation("", "", 0.0, 0.0);
    }

    public boolean isEmpty() {
        return !StringUtils.hasText(carParkNo);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CarParkInformation carParkInformation = (CarParkInformation) o;

        return Objects.equals(carParkNo, carParkInformation.getCarParkNo());

    }

    @Override
    public int hashCode() {
        return Objects.hashCode(carParkNo);
    }
}
