package com.example.demo.service.Interface;

import com.example.demo.dto.response.ExperienceResponse;
import com.example.demo.response.ApiResponse;

import java.util.List;

public interface ExperienceService {
    ApiResponse<List<ExperienceResponse>> getAllExperience();
}
