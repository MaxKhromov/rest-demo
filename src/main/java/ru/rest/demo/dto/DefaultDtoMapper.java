package ru.rest.demo.dto;

import org.modelmapper.ModelMapper;
import org.springframework.data.util.CastUtils;
import org.springframework.expression.ParseException;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

//<D> - DTO
//<E> - Entity
public interface DefaultDtoMapper<D extends DtoBase, E> {

    ModelMapper getModelMapper();


    default D convertToDto(E entity) {
        return CastUtils.cast(getModelMapper().map(entity, getGenericInterfaceType(0)));
    }

    default E convertToEntity(D dto) throws ParseException {
        return CastUtils.cast(getModelMapper().map(dto, getGenericInterfaceType(1)));
    }

    default Class<?> getGenericInterfaceType(int index) {
        Class<?> clazz = getClass();
        ParameterizedType parameterizedType = (ParameterizedType) clazz.getGenericInterfaces()[0];
        Type[] typeArguments = parameterizedType.getActualTypeArguments();
        return (Class<?>) typeArguments[index];
    }

}
