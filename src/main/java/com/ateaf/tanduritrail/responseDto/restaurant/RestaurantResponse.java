package com.ateaf.tanduritrail.responseDto.restaurant;

public record RestaurantResponse(
        Integer id,
        String name,
        String city,
        String state,
        double rating,
        String cuisineType,
        String image
) {
}
