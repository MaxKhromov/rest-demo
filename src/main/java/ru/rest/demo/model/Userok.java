package ru.rest.demo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
public class Userok {
    @Id
    @GenericGenerator(name = "uuid", strategy = "uuid4")
    UUID id;

    String name;
    String email;
    String password;
    Gender gender;
    String phone;

    LocalDateTime createdAt;
    LocalDateTime modifiedAt;


    @PrePersist
    public void prePersist() {
        id = UUID.randomUUID();
        LocalDateTime now = LocalDateTime.now();
        createdAt = now;
        modifiedAt = now;
    }

    @PreUpdate
    public void preUpdate() {
        modifiedAt = LocalDateTime.now();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Userok userok = (Userok) o;
        return id != null && Objects.equals(id, userok.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
