package com.ateaf.tanduritrail.responseDto.restaurant;

public record RestaurantRegistrationResponse(
        Integer Id,
        String name,
        String owner,
        String token
) {
}
