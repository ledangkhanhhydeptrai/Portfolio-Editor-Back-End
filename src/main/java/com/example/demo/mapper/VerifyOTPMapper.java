package com.example.demo.mapper;

import com.example.demo.dto.response.VerifyOtpResponse;
import com.example.demo.entity.PasswordResetOtp;
import org.springframework.stereotype.Component;

@Component
public class VerifyOTPMapper {
    public VerifyOtpResponse toVerifyOtpResponse(PasswordResetOtp passwordResetOtp) {
        if (passwordResetOtp == null) {
            return null;
        }
        return VerifyOtpResponse.builder()
                .resetToken(passwordResetOtp.getResetToken())
                .build();
    }
}
