package ru.rest.demo.rest.exception;

import org.hibernate.FetchNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ExceptionHandlers extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorModel> handle(Exception ex) {

        if (ex instanceof ExceptionBase castedEx) {
            return ResponseEntity.internalServerError()
                    .body(castedEx.mapToError());
        }

        if (ex instanceof FetchNotFoundException castedEx) {
            return ResponseEntity.internalServerError()
                    .body(new CustomEntityNotFoundException(castedEx).mapToError());
        }

        else {
            return ResponseEntity.internalServerError()
                    .body(new CustomUnexpectedException().mapToError());
        }
    }
}
