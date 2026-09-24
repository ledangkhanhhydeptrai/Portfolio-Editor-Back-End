package com.example.demo.service.Interface;

import com.example.demo.dto.response.EducationResponse;
import com.example.demo.response.ApiResponse;

import java.util.List;

public interface EducationService {
    ApiResponse<List<EducationResponse>> getAllEducation();
}
