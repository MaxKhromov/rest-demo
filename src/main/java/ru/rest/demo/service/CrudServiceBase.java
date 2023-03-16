package ru.rest.demo.service;


import jakarta.annotation.Nullable;
import org.hibernate.FetchNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.BindingResult;
import ru.rest.demo.dto.CustomPage;
import ru.rest.demo.model.EntityBase;
import ru.rest.demo.model.Userok;
import ru.rest.demo.repo.RepositoryBase;
import ru.rest.demo.rest.exception.CustomValidationException;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Optional;
import java.util.UUID;

public interface CrudServiceBase<T extends EntityBase<ID>, ID extends Serializable> {
    <F extends RepositoryBase<T, ID>> F getRepository();

    default <S extends T> S save(S entity, @Nullable BindingResult errors) {
        //this is the validation barrier
        if (errors.hasErrors()) {
            throw new CustomValidationException(errors);
        }
        return getRepository().save(entity);
    }

    <S extends T> S update(ID id, S patch, @Nullable BindingResult errors);

    default <S extends T> T findById(ID id) {
        return getRepository().findById(id).orElseThrow(() -> new FetchNotFoundException(getGenericInterfaceType().getSimpleName(), id));
    }

    default CustomPage<T> findAll(String search, Pageable pageable) {
        return new CustomPage<>(getRepository().findAll(pageable));
    }

    default void deleteById(ID id) {
        T byId = findById(id);
        getRepository().delete(byId);
    }

    default Class<?> getGenericInterfaceType(){
        Class<?> clazz = getClass();
        ParameterizedType parameterizedType = (ParameterizedType) clazz.getGenericInterfaces()[0];
        Type[] typeArguments = parameterizedType.getActualTypeArguments();
        return (Class<?>) typeArguments[0];
    }

}
