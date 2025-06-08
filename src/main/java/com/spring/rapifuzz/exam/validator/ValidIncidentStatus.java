package com.spring.rapifuzz.exam.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = IncidentStatusValidator.class)
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidIncidentStatus {
    String message() default "Invalid incident status";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
