package com.exercise.carparking.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class CarParkInformations {
    private List<CarParkInformation> values;

    public static CarParkInformations ofEmpty() {
        return new CarParkInformations(Collections.emptyList());
    }

    public CarParkInformations(List<CarParkInformation> values) {
        if(Objects.isNull(values)) {
            this.values = new ArrayList<>();
        } else {
            this.values = Collections.unmodifiableList(values);
        }
    }

    public List<CarParkInformation> values() {
        return Collections.unmodifiableList(values);
    }

    public boolean isEmpty() {
        return values.isEmpty();
    }
}
