package ru.rest.demo.rest.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
public class ErrorModel {
    List<ErrorMessage> errors;
}
