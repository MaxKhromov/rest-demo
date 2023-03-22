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
import ru.rest.demo.model.Role;
import ru.rest.demo.rest.filters.RoleSpecs;
import ru.rest.demo.service.RoleService;


@Tag(name = "Роли (role-controller)", description = "Работа с ролями")
@RestController
@RequestMapping("roles")
@RequiredArgsConstructor
public class RoleController {
    @Autowired
    private final RoleService roleService;

    @Operation(summary = "Получить список ролей")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "500", description =
                    """
                            code = '500' message = 'Возникла непредвиденная ошибка' \t
                            """)
    })
    @GetMapping()
    public CustomPage<Role> getAll(@Nullable @Parameter(
            name = "filter",
            description = """
                    Параметры фильтрации. Возможные значения и критерии поиска: \t
                    `id` - полное совпадение (Equal); \t
                    `code` - частичное совпадение без учета регистра (LikeIgnoreCase); \t
                    `description` - частичное совпадение без учета регистра (LikeIgnoreCase); \t
                    """,
            example = """
                    {
                        "code": "adm",
                        "description": "адм"
                    }
                    """)
                                           RoleSpecs filter,
                                   @PageableDefault() @ParameterObject @Nullable Pageable pageable) {
        return roleService.findAll(filter, pageable);
    }

    @Operation(summary = "Получить роль")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "500", description =
                    """
                            code = '300' message = 'Объект '%s' с идентификатором '%s' не найден' \t
                            code = '500' message = 'Возникла непредвиденная ошибка' \t
                            """)
    })
    @GetMapping("/{id}")
    public Role getById(@PathVariable Long id) {
        return roleService.findById(id);
    }

    @Operation(summary = "Создать роль")
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
    public Role create(@RequestBody @Validated Role role, BindingResult errors) {
        return roleService.save(role, errors);
    }

    @Operation(summary = "Обновить роль")
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
    public Role update(@PathVariable Long id, @RequestBody @Validated Role patch, BindingResult errors) {
        return roleService.update(id, patch, errors);
    }

    @Operation(summary = "Удалить роль")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "500", description =
                    """
                            code = '300' message = 'Объект '%s' с идентификатором '%s' не найден' \t
                            code = '500' message = 'Возникла непредвиденная ошибка' \t
                            """)
    })
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        roleService.deleteById(id);
    }

}

