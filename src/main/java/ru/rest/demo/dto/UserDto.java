package ru.rest.demo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import ru.rest.demo.model.GenderEnum;
import ru.rest.demo.model.Role;

import java.util.Set;
import java.util.UUID;

@Data
public class UserDto implements DtoBase {
    @Schema(description = "Уникальный идентификатор", example = "7a46b952-e461-4796-9737-3b74e3e7671b")
    UUID id;

    @NotBlank
    @Schema(description = "ФИО", example = "Макано Алексей Викторович")
    String name;

    @NotBlank
    @Email
    @Schema(description = "Электронная почта", example = "makano@makano.ru")
    String email;

    @NotBlank
    @Schema(description = "Пароль", example = "123")
    String password;

    @Schema(description = "Пол", example = "MALE")
    GenderEnum genderEnum;

    @Pattern(regexp = "\\d{10,13}")
    @Schema(description = "Телефон", example = "89171240734")
    String phone;

    @Schema(description = "Роли пользователя")
    private Set<Role> roles;
}
