package ru.rest.demo.rest.exception;

import lombok.Getter;
import org.hibernate.FetchNotFoundException;

import java.util.List;

@Getter
public class CustomEntityNotFoundException extends RuntimeException implements ExceptionBase {
    private final String entityName;
    private final Object identifier;

    public CustomEntityNotFoundException(FetchNotFoundException ex) {
        this.entityName = ex.getEntityName();
        this.identifier = ex.getIdentifier();
    }

    @Override
    public ErrorModel mapToError() {

        ErrorMessage parentErrorMessage = new ErrorMessage(ErrorMessageEnum.ENTITY_NOT_FOUND.getCode(),
                String.format(ErrorMessageEnum.ENTITY_NOT_FOUND.getMessage(), this.getEntityName(), this.getIdentifier()));

        return new ErrorModel(List.of(parentErrorMessage));
    }
}
