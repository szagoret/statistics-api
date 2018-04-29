package com.szagoret.n26.statistics.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PastDateValidator.class)
public @interface PastDate {
    String message() default "{com.szagoret.n26.validtimeframe.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
