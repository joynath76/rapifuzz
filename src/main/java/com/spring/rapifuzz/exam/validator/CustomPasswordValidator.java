package com.spring.rapifuzz.exam.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Optional;

public class CustomPasswordValidator implements ConstraintValidator<ValidInputPassword, String> {

    private final Integer PASS_LEN = 8;
    private final Integer PASS_UPPER_CASE_CNT = 1;
    private final Integer PASS_LOWER_CASE_CNT = 2;
    private final Integer PASS_SPECIAL_CHAR_CNT = 1;

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (!Optional.of(value).isPresent()) {
            return false;
        }
        value = new String(Base64.getDecoder().decode(value), StandardCharsets.UTF_8);
        if (value.length() < PASS_LEN) {
            return false;
        }
        int smallCaseCnt = 0;
        int specialCharCount = 0;
        int capitalCaseCnt = 0;
        for (char ch: value.toCharArray()) {
            if (Character.isDigit(ch)) {
                continue;
            }
            if (Character.isAlphabetic(ch) && Character.isUpperCase(ch)) {
                capitalCaseCnt++;
            } else if (Character.isAlphabetic(ch) && Character.isLowerCase(ch)) {
                smallCaseCnt++;
            }
            else {
                specialCharCount++;
            }
        }
        if (smallCaseCnt != PASS_LOWER_CASE_CNT) {
            return false;
        }
        if (capitalCaseCnt != PASS_UPPER_CASE_CNT) {
            return false;
        }

        if (specialCharCount != PASS_SPECIAL_CHAR_CNT) {
            return false;
        }

        return true;
    }
}
