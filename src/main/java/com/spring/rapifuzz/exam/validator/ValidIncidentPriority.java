package com.spring.rapifuzz.exam.validator;


import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = IncidentPriorityValidator.class)
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidIncidentPriority {
    String message() default "Invalid incident priority";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
