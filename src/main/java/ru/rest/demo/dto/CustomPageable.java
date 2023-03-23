package ru.rest.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CustomPageable {
    int pageNumber;
    int pageSize;
    long totalElements;
}
