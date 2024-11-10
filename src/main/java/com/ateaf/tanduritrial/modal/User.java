package com.ateaf.tanduritrial.modal;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigInteger;
import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;
    private String password;
    private String firstName;
    private String lastName;
    private BigInteger phone;
    private String email;
    private Character status;       // Y - Active , N - inActive

    @Embedded
    private Address address;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(name = "image")
    private byte[] image;
    //user has many roles
    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER,mappedBy = "user")
    private Set<UserRole> userRoles = new HashSet<>();
}
