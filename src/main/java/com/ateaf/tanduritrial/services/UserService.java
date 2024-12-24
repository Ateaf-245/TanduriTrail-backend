package com.ateaf.tanduritrial.services;

import com.ateaf.tanduritrial.mapper.UserMapper;
import com.ateaf.tanduritrial.modal.User;
import com.ateaf.tanduritrial.repository.UserEmailVerificationRepository;
import com.ateaf.tanduritrial.repository.UserRepository;
import com.ateaf.tanduritrial.requestDto.UserRegisterRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.management.relation.RoleInfoNotFoundException;

@RequiredArgsConstructor
@Service
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final UserEmailVerificationRepository emailVerificationRepository;
    private final UserMapper mapper;

    public Integer registerUser(UserRegisterRequest request){
        log.info("Register User request received");
        if(request == null) return null;

        User user =   userRepository.save(mapper.toUser(request));
        emailVerificationRepository.save(mapper.toEmailVerification(user.getId(), user.getEmail()));
        log.info("User register completed successfully, Id: {} ", user.getId());
        return user.getId();
    }
}

