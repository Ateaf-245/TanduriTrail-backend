package com.ateaf.tanduritrail.modal;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigInteger;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "my_sequence")
    @SequenceGenerator(name = "my_sequence", sequenceName = "my_sequence", allocationSize = 1)
    private Integer id;
    private String password;
    private String firstName;
    private String lastName;

    private BigInteger phone;

    @Column( unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private Character status;       // Y - Active , N - inActive

    @Embedded
    private Address address;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(nullable = false)
    private Role role;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private Date createdDate;

    @LastModifiedDate
    private Date modifiedDate;
}
//    @Lob
//    @Basic(fetch = FetchType.LAZY)
//    @Column(name = "image")
//    private byte[] image;
