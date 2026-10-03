package com.example.demo.service.Interface;

import com.example.demo.dto.request.LoginRequest;
import com.example.demo.dto.response.LoginServiceResult;

public interface LoginService {
    LoginServiceResult login(
            LoginRequest request
    );
}
