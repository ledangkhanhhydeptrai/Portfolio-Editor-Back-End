package com.example.demo.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResetPasswordRequest {

    @NotBlank(message = "Reset token is required")
    private String resetToken;

    @NotBlank(message = "New password is required")
    private String newPassword;

    @NotBlank(message = "Confirm password is required")
    private String confirmPassword;
}