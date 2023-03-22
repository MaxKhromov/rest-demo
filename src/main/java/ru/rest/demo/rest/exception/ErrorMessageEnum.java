package ru.rest.demo.rest.exception;

public enum ErrorMessageEnum {
    VALIDATION_ERROR("400", "Ошибка входных данных"),
    PARAM_MISSING("401", "Отсутствует обязательный параметр"),
    PARAM_WRONG_FORMAT("402", "Неверный формат передаваемого значения"),
    UNEXPECTED_ERROR("500", "Возникла непредвиденная ошибка"),
    ENTITY_NOT_FOUND("300", "Объект '%s' с идентификатором '%s' не найден");

    /*
    Для использования при описании контроллера:
    @ApiResponses(value = {
            @ApiResponse(responseCode = "500", description =
                    """
                            code = '300' message = 'Объект '%s' с идентификатором '%s' не найден' \t
                            code = '400' message = 'Ошибка входных данных' \t
                            code = '401' message = 'Отсутствует обязательный параметр' \t
                            code = '402' message = 'Неверный формат передаваемого значения' \t
                            code = '500' message = 'Возникла непредвиденная ошибка' \t
                            """)
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
