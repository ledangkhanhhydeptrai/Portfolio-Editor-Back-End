package com.example.demo.service.Interface;

import com.example.demo.dto.request.CreateProjectRequest;
import com.example.demo.dto.response.ProjectResponse;
import com.example.demo.entity.Project;
import com.example.demo.response.ApiResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

public interface ProjectService {
    ApiResponse<List<ProjectResponse>> getAllProject();
    ApiResponse<ProjectResponse> getProjectById(UUID id);
    ApiResponse<ProjectResponse> createProject(CreateProjectRequest request, MultipartFile thumbnailUrl);
    ApiResponse<ProjectResponse> updateProject(UUID id, CreateProjectRequest request, MultipartFile thumbnailUrl);
    ApiResponse<Void> deleteProject(UUID id);
}
