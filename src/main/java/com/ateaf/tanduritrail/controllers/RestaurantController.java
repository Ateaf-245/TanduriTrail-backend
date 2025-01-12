package com.ateaf.tanduritrail.controllers;

import com.ateaf.tanduritrail.config.JwtUtils;
import com.ateaf.tanduritrail.requestDto.restaurant.UserRestaurantRegistrationRequest;
import com.ateaf.tanduritrail.responseDto.restaurant.RestaurantRegistrationResponse;
import com.ateaf.tanduritrail.responseDto.restaurant.RestaurantResponse;
import com.ateaf.tanduritrail.services.RestaurantService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@Slf4j
@RequestMapping("/api/v1/restaurant")
public class RestaurantController {

    private final RestaurantService restaurantService;
    private final JwtUtils jwtUtils;

    @PostMapping("/register")
    public ResponseEntity<?> registerNewRestaurant(
            @RequestBody @Valid UserRestaurantRegistrationRequest registrationRequest,
            @RequestPart("image") MultipartFile image
            ){
        Map<String,Object> response =  restaurantService.registerNewRestaurant(registrationRequest,image);
        String jwt = jwtUtils.generateToken((String) response.get("owner"));

        RestaurantRegistrationResponse res = new RestaurantRegistrationResponse(
                (Integer) response.get("restaurant_id"),
                (String) response.get("restaurant_name"),
                (String) response.get("owner"),
                jwt
        );
        return new ResponseEntity<>(res, HttpStatus.CREATED);
    }

    @GetMapping()
    public ResponseEntity<List<RestaurantResponse>> getAllRestaurants(){
        return ResponseEntity.ok(restaurantService.getAllRestaurant());
    }

    @GetMapping("/image/restaurant/{imageName}")
    public ResponseEntity<Resource> getImage(@PathVariable String imageName) {
        return ResponseEntity.ok(restaurantService.handleImageFetch(imageName));
    }


    /*
    TODO: 1. add "partner with us" Button the Navbar (before login)
     2. once user click on "partner with us" button, need to redirect to partner home page.
     3.1. show "register your restaurant" button -> redirect to user Registration page (with restaurant=true)
     3.2. once user enter the user detail store it in front end only (after validation) and then show another form to add restaurant details
     3.3. after restaurant details verified, call create restaurant service with user details, restaurant details(including image)
     4. add the user to user table and  then add the restaurant in the restaurant table with user id as owner id

    */

}
