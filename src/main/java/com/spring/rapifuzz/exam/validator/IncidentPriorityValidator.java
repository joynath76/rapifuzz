package com.spring.rapifuzz.exam.validator;


import com.spring.rapifuzz.exam.util.IncidentPriority;
import com.spring.rapifuzz.exam.util.IncidentType;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class IncidentPriorityValidator implements ConstraintValidator<ValidIncidentPriority, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        // Allow null values to avoid conflict with @NotNull
        if (value == null) {
            return true;
        }
        try {
            IncidentPriority.valueOf(value.toUpperCase());
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}

