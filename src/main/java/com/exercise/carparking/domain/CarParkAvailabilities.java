package com.exercise.carparking.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class CarParkAvailabilities {
    private List<CarParkAvailability> values;

    public static CarParkAvailabilities ofEmpty() {
        return new CarParkAvailabilities(Collections.emptyList());
    }

    public CarParkAvailabilities(List<CarParkAvailability> values) {
        if(Objects.isNull(values)) {
            this.values = new ArrayList<>();
        } else {
            this.values = Collections.unmodifiableList(values);
        }
    }

    public List<CarParkAvailability> values() {
        return Collections.unmodifiableList(values);
    }

    public boolean isEmpty() {
        return values.isEmpty();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CarParkAvailabilities carParkAvailabilities = (CarParkAvailabilities) o;

        return Objects.equals(values, carParkAvailabilities.values);
    }
}
