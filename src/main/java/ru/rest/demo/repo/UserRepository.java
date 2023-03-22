package ru.rest.demo.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import ru.rest.demo.model.Userok;

import java.time.LocalDateTime;
import java.util.UUID;

@Repository
public interface UserRepository extends RepositoryBase<Userok, UUID> {
    Page<Userok> findAllByName(String name, Pageable pageable);

    Page<Userok> findAllByCreatedAtBetween(LocalDateTime from, LocalDateTime to, Pageable pageable);

    Userok findByEmail(String email);
}
