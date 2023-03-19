package ru.rest.demo.rest.filters;

import net.kaczmarzyk.spring.data.jpa.domain.Equal;
import net.kaczmarzyk.spring.data.jpa.domain.In;
import net.kaczmarzyk.spring.data.jpa.domain.Like;
import net.kaczmarzyk.spring.data.jpa.domain.LikeIgnoreCase;
import net.kaczmarzyk.spring.data.jpa.web.annotation.And;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
import org.springframework.data.jpa.domain.Specification;
import ru.rest.demo.model.Userok;

@And({
        @Spec(path = "id", spec = Equal.class),
        @Spec(path = "name", spec = LikeIgnoreCase.class),
        @Spec(path = "email", spec = LikeIgnoreCase.class),
        @Spec(path = "gender", spec = In.class),
        @Spec(path = "phone", spec = Equal.class)
})
public interface UserokSpecs extends Specification<Userok> {
}
