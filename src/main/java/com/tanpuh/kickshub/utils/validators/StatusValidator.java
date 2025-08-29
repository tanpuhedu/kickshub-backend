package com.tanpuh.kickshub.utils.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Objects;

public class StatusValidator implements ConstraintValidator<StatusConstraint, Integer> {
    private int min, max;

    @Override
    public void initialize(StatusConstraint constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
        min = constraintAnnotation.min();
        max = constraintAnnotation.max();
    }

    @Override
    public boolean isValid(Integer status, ConstraintValidatorContext constraintValidatorContext) {
        if (Objects.isNull(status))
            return true;

        return status <= max &&  status >= min;
    }
}
