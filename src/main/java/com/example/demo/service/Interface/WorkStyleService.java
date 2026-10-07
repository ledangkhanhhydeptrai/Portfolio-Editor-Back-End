package com.example.demo.service.Interface;

import com.example.demo.dto.request.CreateWorkStyleRequest;
import com.example.demo.dto.response.WorkStyleResponse;
import com.example.demo.response.ApiResponse;

import java.util.List;
import java.util.UUID;

public interface WorkStyleService {

    ApiResponse<WorkStyleResponse> getAllWorkStyle();

    ApiResponse<List<WorkStyleResponse>> getAllWorkStyleByUser();

    ApiResponse<WorkStyleResponse> getWorkStyleById(UUID id);

    ApiResponse<WorkStyleResponse> getWorkStyleByUserId(UUID id);

    ApiResponse<WorkStyleResponse> createWorkStyle(
            CreateWorkStyleRequest request
    );

    ApiResponse<WorkStyleResponse> updateWorkStyle(
            UUID id,
            CreateWorkStyleRequest request
    );

    ApiResponse<Void> deleteWorkStyle(UUID id);
}