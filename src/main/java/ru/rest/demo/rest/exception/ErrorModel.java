package ru.rest.demo.rest.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
public class ErrorModel {
    List<ErrorMessage> errors;
}
