package com.tanpuh.kickshub.utils.validators;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({ FIELD })
@Retention(RUNTIME)
@Constraint(validatedBy = { StatusValidator.class })
public @interface StatusConstraint {
    String message() default "Invalid status";
    int min();
    int max();
    Class<?>[] groups() default { };
    Class<? extends Payload>[] payload() default {};
}
