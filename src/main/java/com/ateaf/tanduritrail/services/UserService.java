package com.ateaf.tanduritrail.services;

import com.ateaf.tanduritrail.exception.UserAlreadyExistsException;
import com.ateaf.tanduritrail.exception.UserNotFoundException;
import com.ateaf.tanduritrail.mapper.UserMapper;
import com.ateaf.tanduritrail.modal.User;
import com.ateaf.tanduritrail.repository.UserEmailVerificationRepository;
import com.ateaf.tanduritrail.repository.UserRepository;
import com.ateaf.tanduritrail.requestDto.UserRegisterRequest;
import com.ateaf.tanduritrail.requestDto.UserRequest;
import com.ateaf.tanduritrail.responseDto.UserResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final UserEmailVerificationRepository emailVerificationRepository;
    private final UserMapper mapper;

    public String registerUser(UserRegisterRequest request){
        log.info("Register User request received : " +request.toString());

        if(userRepository.existsByEmail(request.email()) )
            throw new UserAlreadyExistsException("User Already exist with email ID: "+ request.email());

        User user =   userRepository.save(mapper.toUser(request));
        emailVerificationRepository.save(mapper.toEmailVerification(user.getId(), user.getEmail()));
        log.info("User register completed successfully, Id: {} ", user.getId());
        return user.getEmail();
    }

    public List<UserResponse> getAllUsers() {
        log.info("Fetch All Users request received");
        return userRepository.findAll()
                .stream()
                .map(mapper::fromUser)
                .collect(Collectors.toList());
    }

    public UserResponse getUserById(Integer id) {
        log.info("Fetch user by id request received :: user id: "+id);
        return userRepository.findById(id)
                .filter( user -> user.getStatus() =='Y')
                .map(mapper::fromUser)
                .orElseThrow(() -> new UserNotFoundException("No User present with the Id :"+id));
    }
    public UserResponse getUserByUsername(String username) {
        log.info("Fetch user by id request received :: username: "+username);
        return mapper.fromUser(userRepository.findByEmail(username));
    }
    public List<UserResponse> getAllBuyers() {
       return userRepository.findAll()
                .stream()
                .filter(user -> Objects.equals(user.getRole().getRoleId(), mapper.fetchBuyerRoleName()))
                .map(mapper::fromUser)
                .collect(Collectors.toList());
    }

    public List<UserResponse> getAllSellers() {
        return userRepository.findAll()
                .stream()
                .filter(user -> Objects.equals(user.getRole().getRoleId(), mapper.fetchSellerRoleName()))
                .map(mapper::fromUser)
                .collect(Collectors.toList());
    }

    public UserResponse updateUser(UserRequest userRequest) {
        var user = userRepository.findById(userRequest.id())
                .orElseThrow(() -> new UserNotFoundException("Cannot update :: No user present with id: "+userRequest.id()));

        mergeUserDetails(user, userRequest);
        return mapper.fromUser(userRepository.save(user));
    }

    private void mergeUserDetails(User user, UserRequest request) {
        if(!request.firstName().isBlank()){
            user.setFirstName(request.firstName());
        }
        if(!request.lastName().isBlank()){
            user.setLastName(request.lastName());
        }
        if(!request.address().getStreet().isBlank()){
            user.getAddress().setStreet(request.address().getStreet());
        }
        if(!request.address().getCity().isBlank()){
            user.getAddress().setCity(request.address().getCity());
        }
        if(!request.address().getZip().isBlank()){
            user.getAddress().setZip(request.address().getZip());
        }
        if(!request.address().getState().isBlank()){
            user.getAddress().setState(request.address().getState());
        }
    }

    public Integer deleteUserById(Integer id) {
        User user =  userRepository.findById(id)
                    .orElseThrow(() -> new UserNotFoundException("No User present with id: "+id));
        user.setStatus('N'); // soft delete / In-Active
        userRepository.save(user);
        return 1;
    }
}

