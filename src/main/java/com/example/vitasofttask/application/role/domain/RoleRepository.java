package com.example.vitasofttask.application.role.domain;

import com.example.vitasofttask.application.role.model.RoleType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role getRoleByName(RoleType role);
}
