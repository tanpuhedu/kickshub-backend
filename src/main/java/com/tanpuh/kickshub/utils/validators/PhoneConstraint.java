package com.tanpuh.kickshub.utils.validators;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({ FIELD })
@Retention(RUNTIME)
@Constraint(validatedBy = { PhoneValidator.class })
public @interface PhoneConstraint {
    String message() default "Invalid phone number";
    Class<?>[] groups() default { };
    Class<? extends Payload>[] payload() default {};
}
