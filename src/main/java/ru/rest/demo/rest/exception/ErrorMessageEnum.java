package ru.rest.demo.rest.exception;

public enum ErrorMessageEnum {
    VALIDATION_ERROR("400", "Ошибка входных данных"),
    PARAM_MISSING("401", "Отсутствует обязательный параметр"),
    PARAM_WRONG_FORMAT("402", "Неверный формат передаваемого значения"),
    UNEXPECTED_ERROR("500", "Возникла непредвиденная ошибка"),
    ENTITY_NOT_FOUND("300", "Объект '%s' с идентификатором '%s' не найден");

    /*
    @ApiResponses(value = {
    @ApiResponse(responseCode = "500", description = "code = '400' message = 'Ошибка входных данных'"),
    @ApiResponse(responseCode = "500", description = "code = '401' message = 'Отсутствует обязательный параметр'"),
    @ApiResponse(responseCode = "500", description = "code = '300' message = 'Объект '%s' с идентификатором '%s' не найден'"),
    @ApiResponse(responseCode = "500", description = "code = '402' message = 'Неверный формат передаваемого значения'"),
    @ApiResponse(responseCode = "500", description = "code = '500' message = 'Возникла непредвиденная ошибка'")
    })*/

    private final String code;

    private final String message;

    ErrorMessageEnum(String code, String message) {
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
