package ru.rest.demo.rest;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Nullable;
import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.rest.demo.dto.CustomPage;
import ru.rest.demo.model.Userok;
import ru.rest.demo.rest.exception.ErrorMessageEnum;
import ru.rest.demo.rest.filters.UserokSpecs;
import ru.rest.demo.service.UserokService;

import java.util.UUID;

import static ru.rest.demo.rest.exception.ErrorMessageEnum.ENTITY_NOT_FOUND;


@Tag(name = "Пользователи (user-controller)", description = "Работа с пользователями")
@RestController
@RequestMapping("users")
@RequiredArgsConstructor
public class UserController {
    @Autowired
    private final UserokService userokService;

    @Operation(summary = "Получить список пользователей")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "500", description = "code = '500' message = 'Возникла непредвиденная ошибка'")
    })
    @GetMapping()
    public CustomPage<Userok> getAll(@Nullable UserokSpecs filter,
                                     @PageableDefault() @Nullable Pageable pageable) {
        return userokService.findAll(filter, pageable);
    }

    @Operation(summary = "Создать пользователя")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "500", description = "code = '300' message = 'Объект '%s' с идентификатором '%s' не найден'"),
            @ApiResponse(responseCode = "500", description = "code = '400' message = 'Ошибка входных данных'"),
            @ApiResponse(responseCode = "500", description = "code = '401' message = 'Отсутствует обязательный параметр'"),
            @ApiResponse(responseCode = "500", description = "code = '402' message = 'Неверный формат передаваемого значения'"),
            @ApiResponse(responseCode = "500", description = "code = '500' message = 'Возникла непредвиденная ошибка'")
    })
    @PostMapping()
    public Userok create(@RequestBody @Validated Userok userok, BindingResult errors) {
        return userokService.save(userok, errors);
    }

    @Operation(summary = "Удалить пользователя")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "500", description = "code = '300' message = 'Объект '%s' с идентификатором '%s' не найден'"),
            @ApiResponse(responseCode = "500", description = "code = '500' message = 'Возникла непредвиденная ошибка'")
    })
    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) {
        userokService.deleteById(id);
    }

    @Operation(summary = "Частично обновить пользователя")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "500", description = "code = '300' message = 'Объект '%s' с идентификатором '%s' не найден'"),
            @ApiResponse(responseCode = "500", description = "code = '400' message = 'Ошибка входных данных'"),
            @ApiResponse(responseCode = "500", description = "code = '401' message = 'Отсутствует обязательный параметр'"),
            @ApiResponse(responseCode = "500", description = "code = '402' message = 'Неверный формат передаваемого значения'"),
            @ApiResponse(responseCode = "500", description = "code = '500' message = 'Возникла непредвиденная ошибка'")
    })
    @PutMapping("/{id}")
    public Userok update(@PathVariable UUID id, @RequestBody @Validated Userok patch, BindingResult errors) {
        return userokService.update(id, patch, errors);
    }

    @Operation(summary = "Получить пользователя")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "500", description = "code = '300' message = 'Объект '%s' с идентификатором '%s' не найден'"),
            @ApiResponse(responseCode = "500", description = "code = '500' message = 'Возникла непредвиденная ошибка'")
    })
    @GetMapping("/{id}")
    public Userok getById(@PathVariable UUID id) {
        return userokService.findById(id);
    }
}
