package ru.rest.demo.rest.exception;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import ru.rest.demo.utils.CustomUtils;

import java.util.*;
import java.util.stream.Collectors;

public class CustomValidationException extends RuntimeException implements ExceptionBase {
    private final BindingResult errors;
    private ErrorMessageEnum validationError;

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
        if (error instanceof FieldError) {
            FieldError fieldError = (FieldError) error;
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
        ErrorMessageEnum validationError = ErrorMessageEnum.VALIDATION_ERROR;
        ErrorMessageEnum pramMissingError = ErrorMessageEnum.PARAM_MISSING;
        ErrorMessageEnum pramFormatError = ErrorMessageEnum.PARAM_WRONG_FORMAT;

        ErrorMessage parentError = new ErrorMessage();
        parentError.setCode(validationError.getCode());
        parentError.setMessage(validationError.getMessage());

        Map<String, List<ObjectError>> errorsGroupedByType = CustomUtils.groupListBy(this.errors.getAllErrors(), objectError ->
                Arrays.asList(Objects.requireNonNull(objectError.getCodes())).get(objectError.getCodes().length - 1)
        );

        List<ErrorMessage> children = new ArrayList<>();

        errorsGroupedByType.forEach((type, objectErrorList) -> {
            ErrorMessage message = new ErrorMessage();
            switch (type) {
                case "Email":
                case "Pattern":
                    message.setMessage(pramFormatError.getMessage());
                    message.setCode(pramFormatError.getCode());
                    objectErrorList.forEach(er -> {
                        FieldError validationFieldError = (FieldError) er;
                        message.addParam(validationFieldError.getField());
                    });
                    break;
                case "NotBlank":
                case "NotNull":
                default:
                    message.setMessage(pramMissingError.getMessage());
                    message.setCode(pramMissingError.getCode());
                    objectErrorList.forEach(er -> {
                        FieldError validationFieldError = (FieldError) er;
                        message.addParam(validationFieldError.getField());
                    });

            }
            if(children.stream().noneMatch(errMsg -> errMsg.getCode().equals(message.getCode()))){
                children.add(message);
            } else {
                children.stream().findFirst().filter(errMsg -> errMsg.getCode().equals(message.getCode())).get().addAllParams(message.getParams());
            }
        });


//        message.setMessage(pramMissingError.getMessage());
//        message.setCode(pramMissingError.getCode());
//
//        this.errors.getFieldErrors().forEach(er -> {
//            message.addParam(er.getField());
//        });

//        parentError.setChildren(List.of(message));
        parentError.setChildren(children);

        ErrorModel error = new ErrorModel();
        error.setErrors(List.of(parentError));
        return error;
    }
}
