package com.example.demo.service.Interface;

import com.example.demo.dto.request.RegisterRequest;

public interface RegisterService {
    void registerUser(
            RegisterRequest request
    );
}
