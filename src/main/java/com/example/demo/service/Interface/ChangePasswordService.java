package com.example.demo.service.Interface;

import com.example.demo.dto.request.ChangePasswordRequest;
import com.example.demo.dto.request.ForgotPasswordRequest;
import com.example.demo.dto.request.ResetPasswordRequest;
import com.example.demo.dto.request.VerifyOtpRequest;
import com.example.demo.dto.response.VerifyOtpResponse;
import com.example.demo.response.ApiResponse;

public interface ChangePasswordService {
    ApiResponse<Void> changePassword(ChangePasswordRequest request);

    void sendPasswordResetOTP(String email, String otp);
    ApiResponse<Void> sendOtp(ForgotPasswordRequest request);
    ApiResponse<VerifyOtpResponse> verifyOtp(VerifyOtpRequest request);
    ApiResponse<Void> resetPassword(ResetPasswordRequest resetPasswordRequest);
}
