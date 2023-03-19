package ru.rest.demo.rest;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Nullable;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.rest.demo.dto.CustomPage;
import ru.rest.demo.model.Userok;
import ru.rest.demo.rest.filters.UserokSpecs;
import ru.rest.demo.service.UserokService;

import java.util.UUID;


@Tag(name = "Пользователи (user-controller)", description = "Работа с пользователями")
@RestController
@RequestMapping("users")
@RequiredArgsConstructor
public class UserController {
    @Autowired
    private final UserokService userokService;

    @Operation(summary = "Получить список пользователей")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "500", description =
                    """
                            code = '500' message = 'Возникла непредвиденная ошибка' \t
                            """)
    })
    @GetMapping()

    public CustomPage<Userok> getAll(@Nullable @Parameter(
            name = "filter",
            description = """
                    Параметры фильтрации. Возможные значения и критерии поиска: \t
                    `id` - полное совпадение (Equal); \t
                    `name` - частичное совпадение без учета регистра (LikeIgnoreCase); \t
                    `email` - частичное совпадение без учета регистра (LikeIgnoreCase); \t
                    `gender` - один из перечисленных (In); \t
                    `phone` - полное совпадение (Equal); \t
                    """,
            example = """
                    {
                        "name": "мак",
                        "email": "@",
                        "gender": ["MALE", "FEMALE"]
                    }
                    """)
                                     UserokSpecs filter,
                                     @PageableDefault() @ParameterObject @Nullable Pageable pageable) {
        return userokService.findAll(filter, pageable);
    }

    @Operation(summary = "Получить пользователя")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "500", description =
                    """
                            code = '300' message = 'Объект '%s' с идентификатором '%s' не найден' \t
                            code = '500' message = 'Возникла непредвиденная ошибка' \t
                            """)
    })
    @GetMapping("/{id}")
    public Userok getById(@PathVariable UUID id) {
        return userokService.findById(id);
    }

    @Operation(summary = "Создать пользователя")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "500", description =
                    """
                            code = '400' message = 'Ошибка входных данных' \t
                            code = '401' message = 'Отсутствует обязательный параметр' \t
                            code = '402' message = 'Неверный формат передаваемого значения' \t
                            code = '500' message = 'Возникла непредвиденная ошибка' \t
                            """)
    })
    @PostMapping()
    public Userok create(@RequestBody @Validated Userok userok, BindingResult errors) {
        return userokService.save(userok, errors);
    }

    @Operation(summary = "Обновить пользователя")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "500", description =
                    """
                            code = '300' message = 'Объект '%s' с идентификатором '%s' не найден' \t
                            code = '400' message = 'Ошибка входных данных' \t
                            code = '401' message = 'Отсутствует обязательный параметр' \t
                            code = '402' message = 'Неверный формат передаваемого значения' \t
                            code = '500' message = 'Возникла непредвиденная ошибка' \t
                            """)
    })
    @PutMapping("/{id}")
    public Userok update(@PathVariable UUID id, @RequestBody @Validated Userok patch, BindingResult errors) {
        return userokService.update(id, patch, errors);
    }

    @Operation(summary = "Удалить пользователя")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "500", description =
                    """
                            code = '300' message = 'Объект '%s' с идентификатором '%s' не найден' \t
                            code = '500' message = 'Возникла непредвиденная ошибка' \t
                            """)
    })
    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) {
        userokService.deleteById(id);
    }
}
