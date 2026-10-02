package com.example.demo.service.Interface;

import com.example.demo.dto.request.CreateProfileRequest;
import com.example.demo.dto.request.UpdateProfileRequest;
import com.example.demo.dto.response.ProfileResponse;
import com.example.demo.response.ApiResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProfileService {
    ApiResponse<List<ProfileResponse>> getAllProfile();

    ApiResponse<ProfileResponse> getProfileByUser();

    ApiResponse<ProfileResponse> updateProfileByUser(UpdateProfileRequest updateProfileRequest);

    ApiResponse<ProfileResponse> createProfile(
            CreateProfileRequest createProfileRequest,
            MultipartFile file
    );
    ApiResponse<ProfileResponse> getPublicProfile();
}
