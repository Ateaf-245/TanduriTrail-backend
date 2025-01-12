package com.ateaf.tanduritrail.repository;

import com.ateaf.tanduritrail.modal.restaurant.RestaurantApproval;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RestaurantApprovalRepository extends JpaRepository<RestaurantApproval, Integer> {
    List<RestaurantApproval> findByIsApproved(char value);
}
