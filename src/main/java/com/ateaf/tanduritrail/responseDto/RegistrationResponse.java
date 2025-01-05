package com.ateaf.tanduritrail.responseDto;

import java.util.List;

public record RegistrationResponse(
        String token,
        String username,
        UserResponse details
) {
}
