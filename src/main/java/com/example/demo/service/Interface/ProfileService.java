package com.example.demo.service.Interface;

import com.example.demo.dto.response.ProfileResponse;
import com.example.demo.response.ApiResponse;

import java.util.List;

public interface ProfileService {
    ApiResponse<List<ProfileResponse>> getAllProfile();
}
