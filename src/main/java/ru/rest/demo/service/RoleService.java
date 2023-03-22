package ru.rest.demo.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import ru.rest.demo.model.Role;
import ru.rest.demo.repo.RoleRepository;

@Service
@AllArgsConstructor
@SuppressWarnings({"unchecked"})
public class RoleService implements CrudServiceBase<Role, Long> {

    RoleRepository repository;

    @Override
    public RoleRepository getRepository() {
        return repository;
    }

    @Override
    public Role update(Long id, Role patch, BindingResult errors) {
        Role role = findById(id);
        role.setCode(patch.getCode());
        role.setDescription(patch.getDescription());
        return save(role, errors);
    }
}
