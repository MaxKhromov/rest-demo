package ru.rest.demo.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import ru.rest.demo.model.Userok;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface UserRepository extends PagingAndSortingRepository<Userok, UUID>, CrudRepository<Userok, UUID> {
    Page<Userok> findAllByName(String name, Pageable pageable);
    Page<Userok> findAllByCreateDateTimeBetween(LocalDateTime from, LocalDateTime to, Pageable pageable);

}
