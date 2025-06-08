package com.spring.rapifuzz.exam.validator;


import com.spring.rapifuzz.exam.util.IncidentType;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class IncidentTypeValidator implements ConstraintValidator<ValidIncidentType, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        // Allow null values to avoid conflict with @NotNull
        if (value == null) {
            return true;
        }
        try {
            IncidentType.valueOf(value.toUpperCase());
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}

