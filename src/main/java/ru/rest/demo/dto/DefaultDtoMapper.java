package ru.rest.demo.dto;

import org.modelmapper.ModelMapper;
import org.springframework.expression.ParseException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

//T - DTO
//S - Entity
public interface DefaultDtoMapper<D extends DtoBase, E> {

    ModelMapper getModelMapper();


    default D convertToDto(E entity) {
        return (D) getModelMapper().map(entity, getGenericInterfaceType(0));
    }

    default E convertToEntity(D dto) throws ParseException {
        return (E) getModelMapper().map(dto, getGenericInterfaceType(1));
    }

    default Class<?> getGenericInterfaceType(int index){
        Class<?> clazz = getClass();
        ParameterizedType parameterizedType = (ParameterizedType) clazz.getGenericInterfaces()[0];
        Type[] typeArguments = parameterizedType.getActualTypeArguments();
        return (Class<?>) typeArguments[index];
    }
}
