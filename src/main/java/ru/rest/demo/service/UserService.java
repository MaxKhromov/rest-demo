package ru.rest.demo.service;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.rest.demo.model.Userok;
import ru.rest.demo.repo.UserRepository;


import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService implements ServiceCrud<Userok, UUID> {
    UserRepository repository;


    @Override
    public <S extends Userok> S save(S entity) {
        return null;
    }

    @Override
    public <S extends Userok> Iterable<S> saveAll(Iterable<S> entities) {
        return null;
    }

    @Override
    public Optional<Userok> findById(UUID uuid) {
        return Optional.empty();
    }

    @Override
    public Page<Userok> findAll( String search, Pageable pageable) {
//
//        if (Objects.nonNull(name)) {
//            return repository.findAllByName(name, pageable);
//        }
//        if (Objects.nonNull(date)) {
//            return repository.findAllByCreatedAtBetween(date.atTime(LocalTime.MIN), date.atTime(LocalTime.MAX), pageable);
//        }
        return repository.findAll(pageable);
    }

    @Override
    public Page<Userok> findAllById(Iterable<UUID> uuids, Pageable pageable) {
        return repository.findAllById(uuids, pageable);
    }

    @Override
    public void deleteById(UUID uuid) {

    }

    @Override
    public void delete(Userok entity) {

    }
}
