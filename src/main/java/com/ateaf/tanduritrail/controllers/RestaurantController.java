package com.ateaf.tanduritrail.controllers;

import com.ateaf.tanduritrail.requestDto.RestaurantRegistrationRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/restaurant")
public class RestaurantController {


    @PostMapping("/register")
    public ResponseEntity<?> registerNewRestaurant(@RequestBody RestaurantRegistrationRequest registrationRequest){
        return  null;
    }


}
