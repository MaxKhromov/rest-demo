package ru.rest.demo.rest.exception;

import jakarta.persistence.EntityNotFoundException;
import org.hibernate.FetchNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ExceptionHandlers extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorModel> handle(Exception ex) {
        if(ex instanceof ExceptionBase) {
            ExceptionBase castedEx = (ExceptionBase) ex;
            return ResponseEntity.badRequest()
                    .body(castedEx.mapToError());
        }
        if (ex instanceof FetchNotFoundException){
            FetchNotFoundException castedEx = (FetchNotFoundException) ex;
            return ResponseEntity.badRequest()
                    .body(new CustomEntityNotFoundException(castedEx).mapToError());
        }else {
            return ResponseEntity.badRequest()
                    .body(new CustomUnexpectedException().mapToError());
        }
    }
}
