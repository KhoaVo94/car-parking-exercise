package com.exercise.carparking.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CarParkAvailability {
    Integer totalLots;
    Integer availableLots;
    String lotType;
    CarParkInformation carParkInformation;

    public static CarParkAvailability ofEmpty() {
        return new CarParkAvailability(-1, -1, "", CarParkInformation.ofEmpty());
    }

    public boolean isEmpty() {
        return totalLots < 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CarParkAvailability carParkAvailability = (CarParkAvailability) o;

        return Objects.equals(lotType, carParkAvailability.lotType)
                && Objects.equals(carParkInformation, carParkAvailability.carParkInformation);

    }

    @Override
    public int hashCode() {
        return Objects.hash(lotType, carParkInformation);
    }
}
