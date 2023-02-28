package ru.rest.demo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
public class Userok {
    @Id
    @GenericGenerator(name = "uuid", strategy = "uuid4")
    UUID id;

    String name;

    LocalDateTime createDateTime;

    @PrePersist
    public void prePersist() {
        id = UUID.randomUUID();
        createDateTime = LocalDateTime.now();
    }

}
