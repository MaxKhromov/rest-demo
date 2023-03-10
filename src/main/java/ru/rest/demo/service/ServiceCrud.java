package ru.rest.demo.service;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.rest.demo.model.Userok;

import java.util.Optional;
import java.util.UUID;

public interface ServiceCrud<T, ID> {
    <S extends T> S save(S entity);
    <S extends T> Iterable<S> saveAll(Iterable<S> entities);
    Optional<T> findById(ID id);
    Page<T> findAll(String search, Pageable pageable);
    Page<T> findAllById(Iterable<ID> ids, Pageable pageable);

    void deleteById(ID id);
    void delete(T entity);
}
