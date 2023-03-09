package ru.rest.demo.rest.exception;


import org.springframework.validation.BindingResult;

import java.util.List;

public class CustomUnexpectedException extends RuntimeException implements ExceptionBase {

    public CustomUnexpectedException() {

    }

    @Override
    public ErrorModel mapToError() {
        ErrorMessageEnum unexpectedError = ErrorMessageEnum.UNEXPECTED_ERROR;

        ErrorMessage parentError = new ErrorMessage();
        parentError.setCode(unexpectedError.getCode());
        parentError.setMessage(unexpectedError.getMessage());

        ErrorModel error = new ErrorModel();
        error.setErrors(List.of(parentError));
        return error;
    }
}
