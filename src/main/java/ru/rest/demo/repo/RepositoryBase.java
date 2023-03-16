package ru.rest.demo.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;
import ru.rest.demo.model.EntityBase;

import java.io.Serializable;

@NoRepositoryBean
public interface RepositoryBase<U extends EntityBase, ID extends Serializable> extends JpaRepository<U, ID>, JpaSpecificationExecutor<U> {
}
