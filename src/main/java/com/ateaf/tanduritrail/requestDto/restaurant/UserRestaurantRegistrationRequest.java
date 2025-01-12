package com.ateaf.tanduritrail.requestDto.restaurant;

import com.ateaf.tanduritrail.requestDto.UserRegisterRequest;

public record UserRestaurantRegistrationRequest(
    UserRegisterRequest user,
    RestaurantRegistrationRequest restaurant
) {
}
