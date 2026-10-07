package com.example.demo.service.Interface;

import com.example.demo.dto.request.CreateEducationRequest;
import com.example.demo.dto.response.EducationResponse;
import com.example.demo.response.ApiResponse;

import java.util.List;
import java.util.UUID;

public interface EducationService {
    ApiResponse<EducationResponse> getAllEducation();

    ApiResponse<EducationResponse> getEducationById(UUID id);

    ApiResponse<EducationResponse> createEducation(CreateEducationRequest request);

    ApiResponse<EducationResponse> updateEducation(UUID id, CreateEducationRequest request);

    ApiResponse<Void> deleteEducation(UUID id);

    ApiResponse<List<EducationResponse>> getAllEducationByUser();

    ApiResponse<EducationResponse> getAllEducationByUserId(UUID id);
}
