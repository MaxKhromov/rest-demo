package ru.rest.demo.rest.exception;

import lombok.AllArgsConstructor;
import lombok.Data;


public enum ErrorMessageEnum {
    VALIDATION_ERROR("400","Ошибка входных данных"),
    PARAM_MISSING("401", "Отсутствует обязательный параметр"),
    PARAM_WRONG_FORMAT("402", "Неверный формат передаваемого значения"),
    UNEXPECTED_ERROR("500", "Возникла непредвиденная ошибка"),
    ENTITY_NOT_FOUND("300", "Сущность '%s' с идентификатором '%s' не найдена");

    private String code;

    private String message;

    ErrorMessageEnum(String code, String message){
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
