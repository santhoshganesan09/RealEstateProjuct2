package com.example.RealEstateProjuct.service.serviceImpl;

import com.example.RealEstateProjuct.model.Role;
import com.example.RealEstateProjuct.repo.RoleRepo;
import com.example.RealEstateProjuct.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private  RoleRepo roleRepo;

    @Override
    public Role createRole(String roleName) {
        if(roleRepo.findByName(roleName).isPresent()) {
            throw new IllegalArgumentException("Role already exists: " + roleName);
        }
        Role role = Role.builder().name(roleName).build();
        return roleRepo.save(role);
    }

    @Override
    public Optional<Role> findByName(String roleName) {
        return roleRepo.findByName(roleName);
    }

    @Override
    public List<Role> getAllRoles() {
        return roleRepo.findAll();
    }
}
