package ru.rest.demo.rest;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Nullable;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.rest.demo.dto.CustomPage;
import ru.rest.demo.dto.DefaultDtoMapper;
import ru.rest.demo.dto.UserDto;
import ru.rest.demo.model.Userok;
import ru.rest.demo.rest.filters.UserokSpecs;
import ru.rest.demo.service.UserokService;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


@Tag(name = "Пользователи (user-controller)", description = "Работа с пользователями")
@RestController
@RequestMapping("users")
@RequiredArgsConstructor
public class UserController implements DefaultDtoMapper<UserDto, Userok> {

    private final UserokService userokService;

    private final ModelMapper modelMapper;

    @Operation(summary = "Получить список пользователей")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "500", description =
                    """
                            code = '500' message = 'Возникла непредвиденная ошибка' \t
                            """)
    })
    @GetMapping()
    public CustomPage<UserDto> getAll(@Nullable @Parameter(
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
        CustomPage<Userok> result = userokService.findAll(filter, pageable);
        List<UserDto> resultDtoContent = result.getContent().stream().map(this::convertToDto).toList();
        return new CustomPage<>(resultDtoContent, result.getPageable());
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
    public UserDto getById(@PathVariable UUID id) {
        return convertToDto(userokService.findById(id));
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
    public UserDto create(@RequestBody @Validated UserDto userDto, BindingResult errors) {
        Userok userok = convertToEntity(userDto);
        return convertToDto(userokService.save(userok, errors));
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
    public UserDto update(@PathVariable UUID id, @RequestBody @Validated UserDto patch, BindingResult errors) {
        Userok userokPatch = convertToEntity(patch);
        return convertToDto(userokService.update(id, userokPatch, errors));
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

    @Override
    public ModelMapper getModelMapper() {
        return this.modelMapper;
    }
}
