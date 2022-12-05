package com.exercise.carparking.carparkingapi.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class CarParkInformations {
    private List<CarParkInformation> values;

    public static CarParkInformations ofEmpty() {
        return new CarParkInformations(null);
    }

    public CarParkInformations(List<CarParkInformation> values) {
        if(Objects.isNull(values)) {
            this.values = new ArrayList<>();
        } else {
            this.values = values;
        }
    }

    public List<CarParkInformation> values() {
        return Collections.unmodifiableList(values);
    }

    public void add(CarParkInformations carParkInformations) {
        values.addAll(carParkInformations.values);
    }

    public boolean isEmpty() {
        return values.isEmpty();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CarParkInformations carParkInformations = (CarParkInformations) o;

        return Objects.equals(values, carParkInformations.values);
    }
}
