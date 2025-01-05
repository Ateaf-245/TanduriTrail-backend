package com.ateaf.tanduritrail.requestDto;

import java.util.List;

public record RestaurantRegistrationRequest(
        String name,
        Integer OwnerId,
        String Availability,
        String city,
        String State
) {

}
