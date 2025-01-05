package com.ateaf.tanduritrail.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.ateaf.tanduritrail.modal.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, Integer> {
   @Query("SELECT CASE WHEN COUNT(u) > 0 THEN true ELSE false END FROM User u WHERE u.email = :email")
   boolean existsByEmail(@Param("email") String email);

   User findByEmail(String email);
}
