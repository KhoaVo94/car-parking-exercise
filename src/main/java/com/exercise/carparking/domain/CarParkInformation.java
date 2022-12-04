package com.exercise.carparking.domain;

import com.exercise.carparking.util.DataClassUtil;
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
        return DataClassUtil.equals(this, o);

    }

    @Override
    public int hashCode() {
        return DataClassUtil.hashCode(this);
    }
}
