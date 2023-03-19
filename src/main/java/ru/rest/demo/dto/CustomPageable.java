package ru.rest.demo.dto;

import lombok.Data;

@Data
public class CustomPageable {
    int pageNumber;
    int pageSize;
    long totalElements;

    public CustomPageable(int pageNumber, int pageSize, long totalElements) {

        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
        this.totalElements = totalElements;
    }
}
