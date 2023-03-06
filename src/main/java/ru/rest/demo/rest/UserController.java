package ru.rest.demo.rest;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;
import ru.rest.demo.model.Userok;
import ru.rest.demo.repo.UserRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;
import java.util.UUID;


@Tag(name = "Пользователи (user-controller)", description = "Работа с пользователями")
@RestController
@RequestMapping("users")
@RequiredArgsConstructor
public class UserController {
    @Autowired
    private final UserRepository repository;

    @GetMapping("/hello")
    public String hello() {
        return "hello";
    }

    @Operation(summary = "Получить список пользователей")
    @GetMapping()
    public Page<Userok> getAll(@RequestParam(required = false) String name,
                               @RequestParam(required = false) LocalDate date,
                               @PageableDefault() Pageable pageable) {
        if (Objects.nonNull(name)) {
            return repository.findAllByName(name, pageable);
        }
        if (Objects.nonNull(date)) {
            return repository.findAllByCreatedAtBetween(date.atTime(LocalTime.MIN), date.atTime(LocalTime.MAX), pageable);
        }
        return repository.findAll(pageable);
    }

    @Operation(summary = "Создать пользователя")
    @PostMapping()
    public Userok create(@RequestBody Userok userok) {
        return repository.save(userok);
    }

    @Operation(summary = "Удалить пользователя")
    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) {
        Userok byId = getById(id);
        repository.delete(byId);
    }

    @Operation(summary = "Частично обновить пользователя")
    @PatchMapping("/{id}")
    public Userok update(@PathVariable UUID id, @RequestBody Userok patch) {
        Userok userok = getById(id);
        userok.setName(patch.getName());
        userok.setEmail(patch.getEmail());
        userok.setGender(patch.getGender());
        userok.setPassword(patch.getPassword());
        userok.setPhone(patch.getPhone());
        return repository.save(userok);
    }

    @Operation(summary = "Получить пользователя")
    @GetMapping("/{id}")
    public Userok getById(@PathVariable UUID id) {
        return repository.findById(id).orElseThrow(EntityNotFoundException::new);
    }
}
