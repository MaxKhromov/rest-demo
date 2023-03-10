package ru.rest.demo.rest.exception;

import jakarta.persistence.EntityNotFoundException;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.FetchNotFoundException;

import java.util.List;
@Getter
public class CustomEntityNotFoundException extends RuntimeException implements ExceptionBase{
    private final String entityName;
    private final Object identifier;
    public CustomEntityNotFoundException(FetchNotFoundException ex) {
        this.entityName = ex.getEntityName();
        this.identifier = ex.getIdentifier();
    }

    @Override
    public ErrorModel mapToError() {
        ErrorMessageEnum unexpectedError = ErrorMessageEnum.ENTITY_NOT_FOUND;

        ErrorMessage parentError = new ErrorMessage();
        parentError.setCode(unexpectedError.getCode());
        parentError.setMessage(String.format(unexpectedError.getMessage(), this.getEntityName(), this.getIdentifier()) );

        ErrorModel error = new ErrorModel();
        error.setErrors(List.of(parentError));
        return error;
    }
}
