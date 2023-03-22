package ru.rest.demo.service;

import jakarta.annotation.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import ru.rest.demo.model.Role;
import ru.rest.demo.model.Userok;
import ru.rest.demo.repo.RoleRepository;
import ru.rest.demo.repo.UserRepository;
import ru.rest.demo.rest.exception.CustomValidationException;

import java.util.UUID;

@Service
public class RoleService implements CrudServiceBase<Role, Long>{
    RoleRepository repository;

    @Override
    public RoleRepository getRepository() {
        return repository;
    }

    @Override
    public Role save(Role role, @Nullable BindingResult errors){
        if (errors.hasErrors()) {
            throw new CustomValidationException(errors);
        }

        return getRepository().save(role);
    }

    @Override
    public Role update(Long id, Role patch, BindingResult errors) {
        Role role = findById(id);
        role.setCode(patch.getCode());
        role.setDescription(patch.getDescription());
        return save(role, errors);
    }
}
