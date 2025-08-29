package com.tanpuh.kickshub.utils.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Objects;

public class PhoneValidator implements ConstraintValidator<PhoneConstraint, String> {
    @Override
    public boolean isValid(String phone, ConstraintValidatorContext constraintValidatorContext) {
        if (Objects.isNull(phone))
            return true;

        String digitsOnly = phone.replaceAll("[\\s\\-\\.]", "");

        if (digitsOnly.startsWith("+84"))
            digitsOnly = "0" + digitsOnly.substring(3);

        return digitsOnly.matches("^0\\d{9}$");
    }
}
