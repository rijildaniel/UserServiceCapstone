package com.scaler.userservicecapstone.repositories;

import com.scaler.userservicecapstone.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    List<Role> findByNameIn(List<String> roleName);
}