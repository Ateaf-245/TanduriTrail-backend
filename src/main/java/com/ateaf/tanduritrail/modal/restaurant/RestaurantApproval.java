package com.ateaf.tanduritrail.modal.restaurant;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class RestaurantApproval {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "my_sequence")
    @SequenceGenerator(name = "my_sequence", sequenceName = "my_sequence" , allocationSize = 1)
    private Integer id;
    private Integer restaurantId;
    private String restaurantName;
    private String ownerName;     // firstname + lastname
    private String ownerUsername; // email id
    private Character isApproved;
}
