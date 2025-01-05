package com.ateaf.tanduritrail.responseDto;

import com.ateaf.tanduritrail.modal.Address;
import java.math.BigInteger;

public record UserResponse(
        Integer id,
        String firstName,
        String lastName,
        BigInteger phone,
        String email,
        Character status,
        Address address,
        RoleResponse role
) {
}
