package ru.rest.demo.rest.filters;

import net.kaczmarzyk.spring.data.jpa.domain.Equal;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;

/**
 * @noinspection unused
 */
@Spec(path = "deleted", constVal = "false", spec = Equal.class)
public interface NotDeletedEntity {
}
