package ru.rest.demo.rest.filters;

import net.kaczmarzyk.spring.data.jpa.domain.Equal;
import net.kaczmarzyk.spring.data.jpa.domain.In;
import net.kaczmarzyk.spring.data.jpa.domain.LikeIgnoreCase;
import net.kaczmarzyk.spring.data.jpa.web.annotation.And;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
import org.springframework.data.jpa.domain.Specification;
import ru.rest.demo.model.Role;

@And({
        @Spec(path = "id", spec = Equal.class),
        @Spec(path = "code", spec = LikeIgnoreCase.class),
        @Spec(path = "description", spec = LikeIgnoreCase.class),
})
public interface RoleSpecs extends Specification<Role> {
}
