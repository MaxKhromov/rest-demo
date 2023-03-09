package ru.rest.demo.rest.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ExceptionHandlers extends ResponseEntityExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<ErrorModel> handle(Exception ex) {
        if(ex instanceof ExceptionBase) {
            ExceptionBase newEx = (ExceptionBase) ex;
            return ResponseEntity.badRequest()
                    .body(newEx.mapToError());
        } else {
            return ResponseEntity.badRequest()
                    .body(new CustomUnexpectedException().mapToError());
        }
    }
}
