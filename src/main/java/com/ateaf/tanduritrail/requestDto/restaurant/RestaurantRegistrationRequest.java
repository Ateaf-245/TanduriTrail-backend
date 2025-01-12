package com.ateaf.tanduritrail.requestDto.restaurant;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;

public record RestaurantRegistrationRequest(
        @NotNull(message = "Please provide Restaurant name")
        String name,
        @NotNull(message = "PLease provide the Owner Id")
        Integer OwnerId,
        @NotEmpty(message = "PLease provide City name")
        String city,
        @NotEmpty(message = "PLease provide state name")
        String State,
        @NotEmpty(message = "PLease provide cuisine type")
        String cuisineType
) {

}
