package ru.rest.demo.rest.exception;


import java.util.List;

public class CustomUnexpectedException extends RuntimeException implements ExceptionBase {

    public CustomUnexpectedException() {

    }

    @Override
    public ErrorModel mapToError() {

        ErrorMessage parentErrorMessage = new ErrorMessage(ErrorMessageEnum.UNEXPECTED_ERROR.getCode(), ErrorMessageEnum.UNEXPECTED_ERROR.getMessage());

        return new ErrorModel(List.of(parentErrorMessage));
    }
}
