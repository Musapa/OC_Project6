package com.openclassrooms.paymybuddy.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.openclassrooms.paymybuddy.domain.Role;

@Repository("roleRepository")
public interface RoleRepository extends JpaRepository<Role, Long> {
   
	Role findByRole(String role);
}
