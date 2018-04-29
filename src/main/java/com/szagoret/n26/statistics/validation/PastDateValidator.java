package com.szagoret.n26.statistics.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.Instant;

public class PastDateValidator implements ConstraintValidator<PastDate, Long> {

    @Override
    public boolean isValid(Long value, ConstraintValidatorContext context) {
        if (value == null) {
            return false;
        }
        return Instant.now().toEpochMilli() > value;
    }
}
