package ru.rest.demo.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

import java.util.UUID;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Schema(description = "Роль")
public class Role extends EntityBase<Long>{

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Schema(description = "Уникальный идентификатор", example = "123456")
    Long id;

    @NotBlank
    @Schema(description = "Код роли", example = "ADMIN")
    String code;

    @NotBlank
    @Schema(description = "Наименование роли", example = "Администратор")
    String description;

}
