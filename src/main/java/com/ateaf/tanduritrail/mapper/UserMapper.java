package com.ateaf.tanduritrail.mapper;

import com.ateaf.tanduritrail.exception.UserRoleNotFoundException;
import com.ateaf.tanduritrail.modal.*;
import com.ateaf.tanduritrail.repository.RoleRepository;
import com.ateaf.tanduritrail.requestDto.UserRegisterRequest;
import com.ateaf.tanduritrail.responseDto.RoleResponse;
import com.ateaf.tanduritrail.responseDto.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserMapper {

    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public User toUser(UserRegisterRequest request) {

        Role role = roleRepository.findByName(request.roleName());
        if (role == null)
            throw new UserRoleNotFoundException("Role not found with ID: "+ request.roleName());

        return User.builder()
                .firstName(request.firstName())
                .lastName(request.lastName())
                .password(passwordEncoder.encode(request.password()))
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

    public UserResponse fromUser(User user) {
        return new UserResponse(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getPhone(),
                user.getEmail(),
                user.getStatus(),
                user.getAddress(),
                new RoleResponse(user.getRole().getRoleId(),user.getRole().getName())
        );
    }

    public Integer fetchBuyerRoleName() {
        return roleRepository.findByName("BUYER").getRoleId();
    }

    public Integer fetchSellerRoleName() {
        return roleRepository.findByName("SELLER").getRoleId();
    }
}
