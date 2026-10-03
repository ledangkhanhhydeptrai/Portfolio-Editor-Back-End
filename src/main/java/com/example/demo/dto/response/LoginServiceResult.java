package com.example.demo.dto.response;

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
}