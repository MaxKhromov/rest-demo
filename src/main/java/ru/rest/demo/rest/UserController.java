package ru.rest.demo.rest;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;
import ru.rest.demo.model.Userok;
import ru.rest.demo.repo.UserRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;


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

    @GetMapping()
    public Page<Userok> getAll(@RequestParam(required = false) String name,
                               @RequestParam(required = false) LocalDate date,
                               @PageableDefault(size = 10) Pageable pageable) {
        if(Objects.nonNull(name)){
            return repository.findAllByName(name, pageable);
        }
        if(Objects.nonNull(date)){
            return repository.findAllByCreateDateTimeBetween(date.atTime(LocalTime.MIN), date.atTime(LocalTime.MAX), pageable);
        }
        return repository.findAll(pageable);
    }

    @PostMapping()
    public Userok create(@RequestBody Userok userok) {
        return repository.save(userok);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) {
        Userok byId = getById(id);
        repository.delete(byId);
    }

    @PatchMapping("/{id}")
    public Userok update(@PathVariable UUID id, @RequestBody Userok patch) {
        Userok userok = getById(id);
        userok.setName(patch.getName());
        return repository.save(userok);
    }

    @GetMapping("/{id}")
    public Userok getById(@PathVariable UUID id) {
        return repository.findById(id).orElseThrow(EntityNotFoundException::new);
    }
}
