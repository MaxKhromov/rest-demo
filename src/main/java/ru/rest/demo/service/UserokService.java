package ru.rest.demo.service;

import org.hibernate.FetchNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import ru.rest.demo.model.Userok;
import ru.rest.demo.repo.RepositoryBase;
import ru.rest.demo.repo.UserRepository;
import ru.rest.demo.rest.exception.CustomValidationException;

import java.util.UUID;

@Service
public class UserokService implements CrudServiceBase<Userok, UUID> {
    @Autowired
    UserRepository repository;

    @Override
    public UserRepository getRepository() {
        return repository;
    }

    @Override
    public Userok update(UUID uuid, Userok patch, BindingResult errors) {
        Userok userok = findById(uuid);
        userok.setName(patch.getName());
        userok.setEmail(patch.getEmail());
        userok.setGender(patch.getGender());
        userok.setPassword(patch.getPassword());
        userok.setPhone(patch.getPhone());
        return save(userok, errors);
    }
}
