package ru.rest.demo.rest.exception;

import org.apache.commons.lang3.ObjectUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import ru.rest.demo.utils.CustomUtils;

import java.util.*;
import java.util.stream.Collectors;

public class CustomValidationException extends RuntimeException implements ExceptionBase {
    private final BindingResult errors;

    public CustomValidationException(BindingResult errors) {
        this.errors = errors;
    }

    public List<String> getMessages() {
        return getValidationMessage(this.errors);
    }


    @Override
    public String getMessage() {
        return this.getMessages().toString();
    }

    //demonstrate how to extract a message from the binging result
    private static List<String> getValidationMessage(BindingResult bindingResult) {
        return bindingResult.getAllErrors()
                .stream()
                .map(CustomValidationException::getValidationMessage)
                .collect(Collectors.toList());
    }

    private static String getValidationMessage(ObjectError error) {
        if (error instanceof FieldError fieldError) {
            String className = fieldError.getObjectName();
            String property = fieldError.getField();
            Object invalidValue = fieldError.getRejectedValue();
            String message = fieldError.getDefaultMessage();
            return String.format("%s.%s %s, но оно %s", className, property, message, invalidValue);
        }
        return String.format("%s: %s", error.getObjectName(), error.getDefaultMessage());
    }

    @Override
    public ErrorModel mapToError() {
        //Создание родительского сообщения об ошибке
        ErrorMessage parentErrorMessage = new ErrorMessage(ErrorMessageEnum.VALIDATION_ERROR.getCode(), ErrorMessageEnum.VALIDATION_ERROR.getMessage());

        //Группировка сообщений (ObjectError) по типу аннотации параметра в Entity
        Map<String, List<ObjectError>> errorsGroupedByType = CustomUtils.groupListBy(this.errors.getAllErrors(), objectError ->
                Arrays.asList(Objects.requireNonNull(objectError.getCodes())).get(objectError.getCodes().length - 1)
        );

        //Создание сообщения об ошибке в зависимости от аннотации
        errorsGroupedByType.forEach((type, objectErrorList) -> {
            ErrorMessage message = new ErrorMessage();
            switch (type) {
                case "Email", "Pattern" -> {
                    message.setMessage(ErrorMessageEnum.PARAM_WRONG_FORMAT.getMessage());
                    message.setCode(ErrorMessageEnum.PARAM_WRONG_FORMAT.getCode());
                    objectErrorList.forEach(er -> {
                        FieldError validationFieldError = (FieldError) er;
                        message.addParam(validationFieldError.getField());
                    });
                }
                default -> {
                    message.setMessage(ErrorMessageEnum.PARAM_MISSING.getMessage());
                    message.setCode(ErrorMessageEnum.PARAM_MISSING.getCode());
                    objectErrorList.forEach(er -> {
                        FieldError validationFieldError = (FieldError) er;
                        message.addParam(validationFieldError.getField());
                    });
                }
            }

            //Добавление в дочерние ошибки родительского сообщения, сообщения об ошибках по аннотациям
            ErrorMessage existingErrorMessage = parentErrorMessage.children.stream()
                    .findFirst()
                    .filter(errMsg -> errMsg.getCode().equals(message.getCode()))
                    .orElse(null);
            if (ObjectUtils.isEmpty(existingErrorMessage)) {
                parentErrorMessage.children.add(message);
            } else {
                existingErrorMessage.addAllParams(message.getParams());
            }
        });

        return new ErrorModel(List.of(parentErrorMessage));

    }
}
