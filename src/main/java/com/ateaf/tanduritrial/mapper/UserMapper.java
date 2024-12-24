package com.ateaf.tanduritrial.mapper;

import com.ateaf.tanduritrial.exception.UserRoleNotFoundException;
import com.ateaf.tanduritrial.modal.Address;
import com.ateaf.tanduritrial.modal.Role;
import com.ateaf.tanduritrial.modal.User;
import com.ateaf.tanduritrial.modal.UserEmailVerification;
import com.ateaf.tanduritrial.repository.RoleRepository;
import com.ateaf.tanduritrial.requestDto.UserRegisterRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.management.relation.RoleInfoNotFoundException;

@RequiredArgsConstructor
@Service
public class UserMapper {

    private final RoleRepository roleRepository;

    public User toUser(UserRegisterRequest request) {

        Role role = roleRepository.findById(request.roleId())
                .orElseThrow( () -> new UserRoleNotFoundException("Role not found with ID: "+ request.roleId()));

        return User.builder()
                .firstName(request.firstName())
                .lastName(request.lastname())
                .password(request.password())
                .phone(request.phone())
                .email(request.email())
                .status('Y')
                .address(request.address())
                .role(role)
                .build();
    }

    public UserEmailVerification toEmailVerification(Integer userId, String email) {
        return UserEmailVerification.builder()
                .email(email)
                .userId(userId)
                .status('N')
                .build();
    }
}
