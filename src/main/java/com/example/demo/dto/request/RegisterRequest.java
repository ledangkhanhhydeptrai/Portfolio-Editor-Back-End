package com.example.demo.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {

    @NotBlank
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
    private String username;

    @NotBlank
    @Email
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
    private String email;

    @NotBlank
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
    private String password;
}