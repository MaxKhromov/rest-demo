package ru.rest.demo.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import ru.rest.demo.model.Userok;

import java.time.LocalDateTime;
import java.util.UUID;

@Component
@Repository
public interface UserRepository extends RepositoryBase<Userok, UUID> {
    Page<Userok> findAllByName(String name, Pageable pageable);

    Page<Userok> findAllByCreatedAtBetween(LocalDateTime from, LocalDateTime to, Pageable pageable);

    Userok findByEmail(String email);
}
