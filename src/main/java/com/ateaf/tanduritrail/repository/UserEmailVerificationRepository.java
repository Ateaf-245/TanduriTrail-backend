package com.ateaf.tanduritrail.repository;

import com.ateaf.tanduritrail.modal.UserEmailVerification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserEmailVerificationRepository extends JpaRepository<UserEmailVerification, Integer> {
}
