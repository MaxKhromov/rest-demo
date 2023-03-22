package ru.rest.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Set;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Schema(description = "Роль")
public class Role extends EntityBase<Long> {

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

    @ManyToMany(mappedBy = "roles")
    @JsonIgnore
    @Schema(description = "Пользователи с ролью")
    private Set<Userok> users;

}
