package com.ateaf.tanduritrial.modal;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class UserEmailVerification {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer verificationId;
    private Integer Userid;
    private String email;
    private Integer otp;
}
