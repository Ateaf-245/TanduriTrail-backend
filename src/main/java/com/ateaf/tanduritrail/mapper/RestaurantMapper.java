package com.ateaf.tanduritrail.mapper;

import com.ateaf.tanduritrail.modal.restaurant.Restaurant;
import com.ateaf.tanduritrail.modal.restaurant.RestaurantApproval;
import com.ateaf.tanduritrail.requestDto.restaurant.RestaurantRegistrationRequest;
import com.ateaf.tanduritrail.responseDto.UserResponse;
import com.ateaf.tanduritrail.responseDto.restaurant.RestaurantResponse;
import org.springframework.stereotype.Service;

import java.io.File;


@Service
public class RestaurantMapper {

    public Restaurant toRestaurant(RestaurantRegistrationRequest request, Integer ownerId, String imagePath) {
        return Restaurant.builder()
                .name(request.name())
                .OwnerId(ownerId)
                .city(request.city())
                .state(request.State())
                .cuisineType(request.cuisineType())
                .isActive('Y')
                .imagePath(imagePath)
                .build();
    }

    public RestaurantApproval toRestaurantApproval(Integer restaurantID, String name, UserResponse user) {

        return RestaurantApproval.builder()
                .restaurantName(name)
                .restaurantId(restaurantID)
                .ownerName(user.firstName() +" "+ user.lastName())
                .ownerUsername(user.email())
                .isApproved('N')
                .build();
    }

    public RestaurantResponse fromRestaurant(Restaurant restaurant){

        File file = new File(restaurant.getImagePath());
        String imageUrl = "/image/restaurant/" + file.getName();

        return new RestaurantResponse(
                restaurant.getRestaurantID(),
                restaurant.getName(),
                restaurant.getCity(),
                restaurant.getState(),
                restaurant.getRating(),
                restaurant.getCuisineType(),
                imageUrl
        );
    }
}
