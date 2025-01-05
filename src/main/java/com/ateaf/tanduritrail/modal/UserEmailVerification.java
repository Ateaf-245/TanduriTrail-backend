package com.ateaf.tanduritrail.modal;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
@Entity
public class UserEmailVerification {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "my_sequence")
    @SequenceGenerator(name = "my_sequence", sequenceName = "my_sequence", allocationSize = 1)
    private Integer verificationId;
    private Integer userId;
    private String email;
    private Character status;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private Date createdDate;

    @LastModifiedDate
    private Date modifiedDate;
}
