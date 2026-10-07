package com.example.demo.service.Interface;

import com.example.demo.dto.request.CreateDirectionRequest;
import com.example.demo.dto.response.DirectionResponse;
import com.example.demo.response.ApiResponse;

import java.util.List;
import java.util.UUID;

public interface DirectionService {

    ApiResponse<DirectionResponse> getAllDirection();

    ApiResponse<List<DirectionResponse>> getAllDirectionByUser();

    ApiResponse<DirectionResponse> getDirectionById(UUID id);

    ApiResponse<DirectionResponse> getDirectionByUserId(UUID id);

    ApiResponse<DirectionResponse> createDirection(
            CreateDirectionRequest request
    );

    ApiResponse<DirectionResponse> updateDirection(
            UUID id,
            CreateDirectionRequest request
    );

    ApiResponse<Void> deleteDirection(UUID id);
}