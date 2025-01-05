package com.ateaf.tanduritrail.requestDto;

import com.ateaf.tanduritrail.customValidation.MinPhone;
import com.ateaf.tanduritrail.modal.Address;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.math.BigInteger;

public record UserRegisterRequest(
        @NotEmpty(message = "first name is required")
        String firstName,

        @NotEmpty(message = "last name is required")
        String lastName,

        @NotEmpty(message = "password is required")
        String password,

        @NotNull(message = "Phone number is required")
        @MinPhone(value = "6000000000", message = "Phone number must be at least 10 digits")
        BigInteger phone,

        @NotNull(message = "Email id is required")
        @Email(message = "Email id is not valid")
        String email,

        @NotNull
        String roleName,

        Address address
) {
}
