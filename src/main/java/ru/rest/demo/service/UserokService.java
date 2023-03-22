package ru.rest.demo.service;

import jakarta.annotation.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import ru.rest.demo.model.Userok;
import ru.rest.demo.repo.UserRepository;
import ru.rest.demo.rest.exception.CustomValidationException;

import java.util.UUID;

@Service
public class UserokService implements CrudServiceBase<Userok, UUID> {
    @Autowired
    UserRepository repository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public UserRepository getRepository() {
        return repository;
    }

    @Override
    public Userok save(Userok userok, @Nullable BindingResult errors) {
        if (errors.hasErrors()) {
            throw new CustomValidationException(errors);
        }
        userok.setPassword(passwordEncoder.encode(userok.getPassword()));
        return getRepository().save(userok);
    }

    @Override
    public Userok update(UUID uuid, Userok patch, BindingResult errors) {
        Userok userok = findById(uuid);
        userok.setName(patch.getName());
        userok.setEmail(patch.getEmail());
        userok.setGender(patch.getGender());
        userok.setPassword(passwordEncoder.encode(patch.getPassword()));
        userok.setPhone(patch.getPhone());
        userok.setRoles(patch.getRoles());
        return save(userok, errors);
    }
}
