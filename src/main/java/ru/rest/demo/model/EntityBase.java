package ru.rest.demo.model;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@MappedSuperclass
public abstract class EntityBase<ID extends Serializable>{

//    private ID id;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

//    @Id
//    public ID getId() {
//        return id;
//    }

    @PrePersist
    public void prePersist() {
//        if(getGenericInterfaceType().getSimpleName().equals("UUID")){
//            setId((ID) UUID.randomUUID());
//        } else {
//            setId();
//        }
        LocalDateTime now = LocalDateTime.now();
        this.createdAt = now;
        this.modifiedAt = now;
    }

    @PreUpdate
    public void preUpdate() {
        this.modifiedAt = LocalDateTime.now();
    }

//    private Class<?> getGenericInterfaceType(){
//        Class<?> clazz = getClass();
//        ParameterizedType parameterizedType = (ParameterizedType) clazz.getGenericInterfaces()[0];
//        Type[] typeArguments = parameterizedType.getActualTypeArguments();
//        return (Class<?>) typeArguments[0];
//    }

//    public static abstract Class<?> getCurrentClass();
}
