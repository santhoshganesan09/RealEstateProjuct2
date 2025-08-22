package com.example.RealEstateProjuct.service;

import com.example.RealEstateProjuct.model.Role;

import java.util.List;
import java.util.Optional;

public interface RoleService {

    Role createRole(String roleName);
    Optional<Role> findByName(String roleName);
    List<Role> getAllRoles();
}
