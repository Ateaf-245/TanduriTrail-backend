package com.ateaf.tanduritrial.controllers;

import com.ateaf.tanduritrial.requestDto.UserRegisterRequest;
import com.ateaf.tanduritrial.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<?> registerUser(@Valid @RequestBody UserRegisterRequest request){
        return new ResponseEntity<>(userService.registerUser(request), HttpStatus.CREATED);
    }

}
