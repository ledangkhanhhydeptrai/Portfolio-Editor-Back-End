package com.example.demo.service.Interface;

import com.example.demo.dto.request.CreateExperienceRequest;
import com.example.demo.dto.response.ExperienceResponse;
import com.example.demo.response.ApiResponse;

import java.util.List;
import java.util.UUID;

public interface ExperienceService {
    ApiResponse<List<ExperienceResponse>> getAllExperience();

    ApiResponse<ExperienceResponse> getExperienceById(UUID id);

    ApiResponse<ExperienceResponse> createExperience(CreateExperienceRequest request);

    ApiResponse<ExperienceResponse> updateExperience(UUID id, CreateExperienceRequest request);

    ApiResponse<Void> deleteExperience(UUID id);

    ApiResponse<List<ExperienceResponse>> getAllExperienceByUser();

    ApiResponse<ExperienceResponse> getAllExperienceByUserId(UUID id);
}
