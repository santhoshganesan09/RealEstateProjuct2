package com.example.RealEstateProjuct.service.serviceImpl;

import com.example.RealEstateProjuct.dto.UserDTO;
import com.example.RealEstateProjuct.exception.ResourceNotFoundException;
import com.example.RealEstateProjuct.mapper.UserMapper;
import com.example.RealEstateProjuct.model.Role;
import com.example.RealEstateProjuct.model.User;
import com.example.RealEstateProjuct.repo.RoleRepo;
import com.example.RealEstateProjuct.repo.UserRepo;
import com.example.RealEstateProjuct.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {


    private final UserRepo userRepo;


    private final RoleRepo roleRepo;


    private final UserMapper userMapper;


    private final BCryptPasswordEncoder passwordEncoder; // register bean in config

    @Override
    public UserDTO createUser(UserDTO userDTO) {
        log.info("Creating user with email={}", userDTO.getEmail());
        if (userRepo.existsByEmail(userDTO.getEmail())) {
            throw new IllegalArgumentException("Email already in use");
        }

        User user = userMapper.toEntity(userDTO);

        // hash password
        if (userDTO.getPassword() == null || userDTO.getPassword().isBlank()) {
            throw new IllegalArgumentException("Password is required");
        }
        user.setPasswordHash(passwordEncoder.encode(userDTO.getPassword()));

        // resolve roles from names (create if not present? - here we require roles exist)
        Set<Role> roles = resolveRoles(userDTO.getRoles());
        user.setRoles(roles);

        // default status
        if (user.getStatus() == null) user.setStatus(com.example.RealEstateProjuct.enumClass.UserStatus.ACTIVE);

        User saved = userRepo.save(user);
        return userMapper.toDto(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public UserDTO getUserById(Long id) {
        log.info("Fetching user id={}", id);
        User user = userRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
        return userMapper.toDto(user);
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserDTO> getAllUsers() {
        log.info("Fetching all users");
        return userRepo.findAll()
                .stream()
                .map(userMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public UserDTO updateUser(Long id, UserDTO userDTO) {
        log.info("Updating user id={}", id);
        User existing = userRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));

        // update simple fields
        existing.setFullName(userDTO.getFullName());
        existing.setPhone(userDTO.getPhone());
        existing.setCompanyName(userDTO.getCompanyName());
        if (userDTO.getStatus() != null) {
            existing.setStatus(Enum.valueOf(com.example.RealEstateProjuct.enumClass.UserStatus.class, userDTO.getStatus()));
        }

        // update email carefully
        if (!existing.getEmail().equalsIgnoreCase(userDTO.getEmail())) {
            if (userRepo.existsByEmail(userDTO.getEmail())) {
                throw new IllegalArgumentException("Email already in use");
            }
            existing.setEmail(userDTO.getEmail());
        }

        // update password if provided
        if (userDTO.getPassword() != null && !userDTO.getPassword().isBlank()) {
            existing.setPasswordHash(passwordEncoder.encode(userDTO.getPassword()));
        }

        // roles update (optional)
        if (userDTO.getRoles() != null) {
            existing.setRoles(resolveRoles(userDTO.getRoles()));
        }

        User saved = userRepo.save(existing);
        return userMapper.toDto(saved);
    }

    @Override
    public void deleteUser(Long id) {
        log.info("Deleting user id={}", id);
        if (!userRepo.existsById(id)) {
            throw new ResourceNotFoundException("User not found with id: " + id);
        }
        userRepo.deleteById(id);
    }

    private Set<Role> resolveRoles(List<String> roleNames) {
        if (roleNames == null || roleNames.isEmpty()) {
            // default role USER
            Role defaultRole = roleRepo.findByName("USER")
                    .orElseThrow(() -> new IllegalArgumentException("Default role USER not found"));
            return Set.of(defaultRole);
        }

        Set<Role> roles = new HashSet<>();
        for (String rn : roleNames) {
            Role role = roleRepo.findByName(rn)
                    .orElseThrow(() -> new IllegalArgumentException("Role not found: " + rn));
            roles.add(role);
        }
        return roles;
    }

}
