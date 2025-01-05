package com.ateaf.tanduritrail.requestDto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public record LoginRequest(

        @NotNull(message = "Email id is required")
        @Email(message = "Email id is not valid")
        String email,

        @NotNull(message = "Password cannot be empty")
        String password
) {
}

