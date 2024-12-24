package com.ateaf.tanduritrial.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.ateaf.tanduritrial.modal.User;

public interface UserRepository extends JpaRepository<User, Integer> {
}
