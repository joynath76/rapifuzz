package com.spring.rapifuzz.exam.validator;

import com.spring.rapifuzz.exam.util.IncidentStatus;
import com.spring.rapifuzz.exam.util.IncidentType;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class IncidentStatusValidator implements ConstraintValidator<ValidIncidentStatus, String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        if (value == null) {
            return true;
        }
        try {
            IncidentStatus.valueOf(value.toUpperCase());
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}
