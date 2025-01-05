package com.ateaf.tanduritrail.requestDto;

import com.ateaf.tanduritrail.modal.Address;
import jakarta.validation.constraints.NotEmpty;

public record UserRequest(
        Integer id,

        @NotEmpty(message = "first name is required")
        String firstName,

        @NotEmpty(message = "last name is required")
        String lastName,

        Address address
) {
}
