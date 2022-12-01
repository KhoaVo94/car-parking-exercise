package com.exercise.carparking.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Pageable {
    Integer pageNumber;
    Integer pageSize;

    public Integer getOffset() {
        return (pageNumber - 1) * pageSize;
    }

    public Integer getCurrentItemSize() {
        return getOffset() + pageSize;
    }

    public void increasePageNumber(int increament) {
        pageNumber = pageNumber + increament;
    }

    public void increasePageSize(int increament) {
        pageSize = pageSize + increament;
    }
}
