package ru.rest.demo.service;


import jakarta.annotation.Nullable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.BindingResult;
import ru.rest.demo.model.Userok;

import java.util.Optional;
import java.util.UUID;

public interface ServiceCrud<T, ID> {
    <S extends T> S save(S entity, @Nullable BindingResult errors);
    <S extends T> S update(ID id, S patch, @Nullable BindingResult errors);
    <S extends T> S findById(ID id);
    Page<T> findAll(String search, Pageable pageable);
    void deleteById(ID id);
}
