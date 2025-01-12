package com.ateaf.tanduritrail.repository;

import com.ateaf.tanduritrail.modal.restaurant.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RestaurantRepository extends JpaRepository<Restaurant,Integer> {
}
