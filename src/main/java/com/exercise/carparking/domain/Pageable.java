package com.exercise.carparking.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Pageable {
    Integer pageNumber;
    Integer pageSize;

    public Integer getOffset() {
        return pageNumber * pageSize;
    }
}
