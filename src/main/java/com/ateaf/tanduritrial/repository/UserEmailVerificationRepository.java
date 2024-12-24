package com.ateaf.tanduritrial.repository;

import com.ateaf.tanduritrial.modal.UserEmailVerification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserEmailVerificationRepository extends JpaRepository<UserEmailVerification, Integer> {
}
