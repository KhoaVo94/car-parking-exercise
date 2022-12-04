package com.exercise.carparking.domain;

import com.exercise.carparking.util.DataClassUtil;
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
        return DataClassUtil.equals(this, o);

    }

    @Override
    public int hashCode() {
        return DataClassUtil.hashCode(this);
    }
}
