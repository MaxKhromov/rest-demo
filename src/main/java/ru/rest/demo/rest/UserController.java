package ru.rest.demo.rest;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.rest.demo.dto.CustomPage;
import ru.rest.demo.model.Userok;
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
    @GetMapping()
    public CustomPage<Userok> getAll(@RequestParam(required = false) String search,
                                     @PageableDefault() Pageable pageable) {
        return userokService.findAll(search, pageable);
    }

    @Operation(summary = "Создать пользователя")
    @PostMapping()
    public Userok create(@RequestBody @Validated Userok userok, BindingResult errors) {
        return userokService.save(userok, errors);
    }

    @Operation(summary = "Удалить пользователя")
    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) {
        userokService.deleteById(id);
    }

    @Operation(summary = "Частично обновить пользователя")
    @PatchMapping("/{id}")
    public Userok update(@PathVariable UUID id, @RequestBody @Validated Userok patch, BindingResult errors) {
        return userokService.update(id, patch, errors);
    }

    @Operation(summary = "Получить пользователя")
    @GetMapping("/{id}")
    public Userok getById(@PathVariable UUID id) {
        return userokService.findById(id);
    }
}
