package ru.rest.demo.model;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.SchemaProperty;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.context.annotation.Description;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.io.Serializable;
import java.util.*;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Schema(description = "Пользователь")
public class Userok extends EntityBase<UUID> implements UserDetails {

    @Id
    @GenericGenerator(name = "uuid", strategy = "uuid4")
    @GeneratedValue(strategy = GenerationType.UUID)
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
    Gender gender;

    @Pattern(regexp = "\\d{10,13}")
    @Schema(description = "Телефон", example = "89171240734")
    String phone;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Userok userok = (Userok) o;
        return getId() != null && Objects.equals(getId(), userok.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    @Schema(hidden = true)
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();
        authorities.add(new SimpleGrantedAuthority("USER"));
        return authorities;
    }

    @Override
    @Schema(hidden = true)
    public String getUsername() {
        return this.email;
    }

    @Override
    @Schema(hidden = true)
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    @Schema(hidden = true)
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    @Schema(hidden = true)
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    @Schema(hidden = true)
    public boolean isEnabled() {
        return true;
    }
}
