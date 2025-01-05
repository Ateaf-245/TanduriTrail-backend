package com.ateaf.tanduritrail.controllers;

import com.ateaf.tanduritrail.config.JwtUtils;
import com.ateaf.tanduritrail.requestDto.LoginRequest;
import com.ateaf.tanduritrail.requestDto.UserRegisterRequest;
import com.ateaf.tanduritrail.requestDto.UserRequest;
import com.ateaf.tanduritrail.responseDto.LoginResponse;
import com.ateaf.tanduritrail.responseDto.RegistrationResponse;
import com.ateaf.tanduritrail.responseDto.UserResponse;
import com.ateaf.tanduritrail.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@Slf4j
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest){
        Authentication authentication;
        try{
            authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.email(), loginRequest.password()));
        }catch (AuthenticationException exception){
            Map<String, Object> body = new HashMap<>();
            body.put("status", false);
            body.put("message", "Bad Credentials");
            return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
        }

        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        log.info("user details : {}",userDetails);

        String jwt = jwtUtils.generateToken(userDetails.getUsername());
        List<String> role = userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();
        LoginResponse response = new LoginResponse(jwt,userDetails.getUsername(), role);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody UserRegisterRequest request) {
        UserResponse user  = userService.registerUser(request);
        String jwt = jwtUtils.generateToken(user.email());
        return new ResponseEntity<>(new RegistrationResponse(jwt,user.email(), user), HttpStatus.CREATED);
    }
    @PutMapping("/{id}")
    public ResponseEntity<UserResponse> updateUser(@RequestBody UserRequest userRequest) {
        return ResponseEntity.ok(userService.updateUser(userRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Integer> deleteUserById(@PathVariable Integer id) {
        return ResponseEntity.ok(userService.deleteUserById(id));
    }

    @GetMapping()
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable Integer id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @GetMapping("/buyer")
    public ResponseEntity<List<UserResponse>> getAllBuyers() {
        return ResponseEntity.ok(userService.getAllBuyers());
    }

    @GetMapping("/seller")
    public ResponseEntity<List<UserResponse>> getAllSellers() {
        return ResponseEntity.ok(userService.getAllSellers());
    }


}
