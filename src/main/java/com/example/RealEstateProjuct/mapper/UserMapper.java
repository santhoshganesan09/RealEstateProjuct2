package com.example.RealEstateProjuct.mapper;

import com.example.RealEstateProjuct.dto.UserDTO;
import com.example.RealEstateProjuct.model.Role;
import com.example.RealEstateProjuct.model.User;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.stream.Collectors;

@Component
public class UserMapper {

    public UserDTO toDto(User user) {
        if (user == null) return null;
        return UserDTO.builder()
                .id(user.getId())
                .fullName(user.getFullName())
                .email(user.getEmail())
                // do not set password in DTO response
                .phone(user.getPhone())
                .companyName(user.getCompanyName())
                .status(user.getStatus() != null ? user.getStatus().name() : null)
                .roles(user.getRoles() == null ? Collections.emptyList() :
                        user.getRoles().stream().map(Role::getName).collect(Collectors.toList()))
                .build();
    }

    /**
     * Convert DTO -> Entity WITHOUT hashing password.
     * ServiceImpl is responsible for hashing and setting passwordHash.
     */
    public User toEntity(UserDTO dto) {
        if (dto == null) return null;
        User user = User.builder()
                .id(dto.getId())
                .fullName(dto.getFullName())
                .email(dto.getEmail())
                .phone(dto.getPhone())
                .companyName(dto.getCompanyName())
                .build();

        if (dto.getStatus() != null) {
            try {
                user.setStatus(Enum.valueOf(com.example.RealEstateProjuct.enumClass.UserStatus.class, dto.getStatus()));
            } catch (IllegalArgumentException e) {
                user.setStatus(com.example.RealEstateProjuct.enumClass.UserStatus.INACTIVE);
            }
        }
        return user;
    }

}
