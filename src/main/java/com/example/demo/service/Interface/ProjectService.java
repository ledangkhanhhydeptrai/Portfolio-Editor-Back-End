package com.example.demo.service.Interface;

import com.example.demo.dto.response.ProjectResponse;
import com.example.demo.entity.Project;
import com.example.demo.response.ApiResponse;

import java.util.List;
import java.util.UUID;

public interface ProjectService {
    ApiResponse<List<ProjectResponse>> getAllProject();
    ApiResponse<ProjectResponse> getProjectById(UUID id);
}
