package com.example.demo.dto.response;

import com.example.demo.Enum.RoleEnum;
import com.example.demo.entity.Role;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponse {

    private String username;
    private String email;
    private RoleEnum role;
}