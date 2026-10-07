package com.example.demo.service.Interface;

import com.example.demo.dto.request.CreateCVRequest;
import com.example.demo.dto.response.CVResponse;
import com.example.demo.response.ApiResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

public interface CVService {
    ApiResponse<CVResponse> getAllCV();

    ApiResponse<CVResponse> getCVById(UUID id);

    ApiResponse<CVResponse> createCV(CreateCVRequest request);

    ApiResponse<CVResponse> updateCV(UUID id, CreateCVRequest request);

    ApiResponse<Void> deleteCV(UUID id);

    ApiResponse<List<CVResponse>> getAllCVByUser();

    ApiResponse<CVResponse> getCVByUserId(UUID id);
}
