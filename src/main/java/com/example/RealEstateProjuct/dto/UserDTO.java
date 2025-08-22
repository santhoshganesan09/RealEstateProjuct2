package com.example.RealEstateProjuct.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDTO {

    private Long id;

    @NotBlank(message = "Full name is required")
    @Size(max = 150)
    private String fullName;

    @NotBlank(message = "Email is required")
    @Email(message = "Email should be valid")
    private String email;

    /**
     * Accept plain password only on create/update requests.
     * We DO NOT return password in responses.
     */
    private String password;

    @Size(max = 20)
    private String phone;

    @Size(max = 255)
    private String companyName;

    private String status; // "ACTIVE" or "INACTIVE"

    /**
     * Role names like ["AGENT","USER"]
     */
    private List<String> roles;


}
