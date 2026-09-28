package com.example.demo.service.Implement;

import com.example.demo.dto.response.ProjectResponse;
import com.example.demo.entity.Project;
import com.example.demo.exception.BadRequestException;
import com.example.demo.mapper.ProjectMapper;
import com.example.demo.repository.ProjectRepository;
import com.example.demo.response.ApiResponse;
import com.example.demo.service.Interface.ProjectService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ProjectServiceImpl implements ProjectService {
    private final ProjectRepository projectRepository;
    private final ProjectMapper projectMapper;

    public ProjectServiceImpl(ProjectRepository projectRepository, ProjectMapper projectMapper) {
        this.projectRepository = projectRepository;
        this.projectMapper = projectMapper;
    }

    @Override
    public ApiResponse<List<ProjectResponse>> getAllProject() {
        List<Project> projects = projectRepository.findAllByOrderByDisplayOrderAsc();
        List<ProjectResponse> response = projects.stream().map(projectMapper::toProjectResponse).toList();
        return ApiResponse.<List<ProjectResponse>>builder()
                .status(200)
                .message("Get All Project Successfully")
                .data(response)
                .build();
    }

    @Override
    public ApiResponse<ProjectResponse> getProjectById(UUID id) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new BadRequestException("Project Not Found"));
        ProjectResponse projectResponse = projectMapper.toProjectResponse(project);
        return ApiResponse.<ProjectResponse>builder()
                .status(200)
                .message("Get Project By Id Successfully")
                .data(projectResponse)
                .build();
    }
}
