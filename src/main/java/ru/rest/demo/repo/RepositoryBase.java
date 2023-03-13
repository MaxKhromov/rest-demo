package ru.rest.demo.repo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;
import ru.rest.demo.model.EntityBase;
import ru.rest.demo.model.Userok;

import java.io.Serializable;
import java.util.UUID;

@NoRepositoryBean
public interface RepositoryBase<U extends EntityBase, ID extends Serializable> extends PagingAndSortingRepository<U, ID>, CrudRepository<U, ID> {
}
