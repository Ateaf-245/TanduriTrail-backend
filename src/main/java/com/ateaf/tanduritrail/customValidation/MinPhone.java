package com.ateaf.tanduritrail.customValidation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = MinPhoneValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface MinPhone {

    String message() default "Phone number is too small";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
    String value();
}
