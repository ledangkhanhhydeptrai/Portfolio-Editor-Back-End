package com.example.demo.service.Interface;

import com.example.demo.dto.request.ChangePasswordRequest;
import com.example.demo.response.ApiResponse;

public interface ChangePasswordService {
    ApiResponse<Void> changePassword(ChangePasswordRequest request);
}
