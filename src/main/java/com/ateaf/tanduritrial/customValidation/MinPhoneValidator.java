package com.ateaf.tanduritrial.customValidation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.math.BigInteger;

public class MinPhoneValidator implements ConstraintValidator<MinPhone, BigInteger> {

    private BigInteger minValue;
    @Override
    public void initialize(MinPhone constraintAnnotation) {
        this.minValue = new BigInteger(constraintAnnotation.value());
    }

    @Override
    public boolean isValid(BigInteger phone, ConstraintValidatorContext context) {
        if (phone == null) {
            return false;
        }
        return phone.compareTo(minValue) >= 0;
    }
}
