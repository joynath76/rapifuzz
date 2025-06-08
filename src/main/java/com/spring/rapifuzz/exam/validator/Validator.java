package com.spring.rapifuzz.exam.validator;

import org.springframework.util.StringUtils;

import java.util.Objects;
import java.util.Optional;

public class Validator {

    public static void notNull(Object value, String field) {
        if (Objects.isNull(value)) {
            throw new IllegalArgumentException(String.format("%s cannot be null", field));
        }
    }

    public static void notNull(Object value) {
        notNull(value, "Value");
    }
}
