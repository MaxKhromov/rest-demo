package ru.rest.demo.service;

import jakarta.persistence.EntityNotFoundException;
import org.hibernate.FetchNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import ru.rest.demo.model.Userok;
import ru.rest.demo.repo.UserRepository;
import ru.rest.demo.rest.exception.CustomValidationException;

import java.util.Optional;
import java.util.UUID;

@Service
public class UserokService implements ServiceCrud<Userok, UUID> {
    @Autowired
    UserRepository repository;


    @Override
    public Userok save(Userok entity, BindingResult errors) {
        //this is the validation barrier
        if (errors.hasErrors()) {
            throw new CustomValidationException(errors);
        }
        return repository.save(entity);
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

    @Override
    public Userok findById(UUID uuid) {
        return repository.findById(uuid).orElseThrow(() -> new FetchNotFoundException(Userok.class.getSimpleName(), uuid));
    }

    @Override
    public Page<Userok> findAll(String search, Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public void deleteById(UUID uuid) {
        Userok byId = findById(uuid);
        repository.delete(byId);
    }

}
