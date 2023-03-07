package ru.rest.demo.rest.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionHandlers {

    @ExceptionHandler
    public ResponseEntity<ErrorModel> handle(CustomValidationException ex) {
        return ResponseEntity.badRequest()
                .body(ex.mapToError());
    }
}
