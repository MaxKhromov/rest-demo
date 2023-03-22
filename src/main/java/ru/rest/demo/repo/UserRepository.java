package ru.rest.demo.repo;

import org.springframework.stereotype.Repository;
import ru.rest.demo.model.Userok;

import java.util.UUID;

@Repository
public interface UserRepository extends RepositoryBase<Userok, UUID> {
    Userok findByEmail(String email);
}
