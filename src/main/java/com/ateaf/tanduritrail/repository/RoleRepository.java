package com.ateaf.tanduritrail.repository;

import com.ateaf.tanduritrail.modal.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role,Integer> {
    public Role findByName(String name);
}
