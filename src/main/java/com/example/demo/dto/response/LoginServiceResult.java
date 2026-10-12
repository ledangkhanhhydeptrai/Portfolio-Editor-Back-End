package com.example.demo.dto.response;

import com.example.demo.Enum.RoleEnum;
import com.example.demo.entity.Role;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginServiceResult {

    private String accessToken;
    private String refreshToken;

    private String username;
    private String email;
    private RoleEnum role;
}