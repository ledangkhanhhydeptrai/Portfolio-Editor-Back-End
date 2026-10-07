package com.example.demo.service.Implement;

import com.example.demo.dto.request.CreateProjectRequest;
import com.example.demo.dto.response.ProjectResponse;
import com.example.demo.entity.Project;
import com.example.demo.entity.User;
import com.example.demo.exception.BadRequestException;
import com.example.demo.mapper.ProjectMapper;
import com.example.demo.repository.ProjectRepository;
import com.example.demo.response.ApiResponse;
import com.example.demo.service.Interface.AuthService;
import com.example.demo.service.Interface.CloudinaryService;
import com.example.demo.service.Interface.ProjectService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@Service
public class ProjectServiceImpl implements ProjectService {
    @Value("${portfolio.owner.email}")
    private String portfolioOwnerEmail;
    private final ProjectRepository projectRepository;
    private final ProjectMapper projectMapper;
    private final CloudinaryService cloudinaryService;
    private final AuthService authService;

    public ProjectServiceImpl(ProjectRepository projectRepository, ProjectMapper projectMapper, CloudinaryService cloudinaryService, AuthService authService) {
        this.projectRepository = projectRepository;
        this.projectMapper = projectMapper;
        this.cloudinaryService = cloudinaryService;
        this.authService = authService;
    }

    @Override
    public ApiResponse<ProjectResponse> getAllProject() {
        Project projects = projectRepository.findFirstByUser_EmailOrderByDisplayOrderAsc(portfolioOwnerEmail)
                .orElseThrow(() -> new BadRequestException("Project Public Not Found"));
        ProjectResponse response = projectMapper.toProjectResponse(projects);
        return ApiResponse.<ProjectResponse>builder()
                .status(200)
                .message("Get All Project Successfully")
                .data(response)
                .build();
    }

    @Override
    public ApiResponse<List<ProjectResponse>> getAllProjectByUser() {
        User user = authService.getCurrentUser();
        List<Project> projects = projectRepository.findAllByUserOrderByDisplayOrderAsc(user);
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

    @Override
    public ApiResponse<ProjectResponse> getProjectByIdAndUser(UUID id) {
        User user = authService.getCurrentUser();
        Project project = projectRepository.findByIdAndUser(id, user)
                .orElseThrow(() -> new BadRequestException("Project Not Found"));
        ProjectResponse projectResponse = projectMapper.toProjectResponse(project);
        return ApiResponse.<ProjectResponse>builder()
                .status(200)
                .message("Get Project By Id And User Successfully")
                .data(projectResponse)
                .build();
    }

    @Override
    public ApiResponse<ProjectResponse> createProject(CreateProjectRequest request, MultipartFile thumbnailUrl) {
        User user = authService.getCurrentUser();
        Project project = new Project();
        project.setDisplayOrder(request.getDisplayOrder());
        project.setDescription(request.getDescription());
        project.setDemoUrl(request.getDemoUrl());
        project.setCategory(request.getCategory());
        project.setGithubUrl(request.getGithubUrl());
        project.setTitle(request.getTitle());
        project.setFeatured(request.getFeatured());
        project.setUser(user);
        try {
            String image = cloudinaryService.uploadFile(thumbnailUrl);
            project.setThumbnailUrl(image);
        } catch (Exception e) {
            throw new BadRequestException("Image Upload Failed");
        }
        Project projectCreated = projectRepository.save(project);
        ProjectResponse projectResponse = projectMapper.toProjectResponse(projectCreated);
        return ApiResponse.<ProjectResponse>builder()
                .status(200)
                .message("Create Project Successfully")
                .data(projectResponse)
                .build();
    }

    @Override
    public ApiResponse<ProjectResponse> updateProject(UUID id, CreateProjectRequest request, MultipartFile thumbnailUrl) {
        User user = authService.getCurrentUser();
        Project project = projectRepository.findByIdAndUser(id, user)
                .orElseThrow(() -> new BadRequestException("Project Not Found"));
        project.setUser(user);
        project.setDisplayOrder(request.getDisplayOrder());
        project.setDescription(request.getDescription());
        project.setDemoUrl(request.getDemoUrl());
        project.setCategory(request.getCategory());
        project.setGithubUrl(request.getGithubUrl());
        project.setTitle(request.getTitle());
        project.setFeatured(request.getFeatured());
        try {
            String image = cloudinaryService.uploadFile(thumbnailUrl);
            project.setThumbnailUrl(image);
        } catch (Exception e) {
            throw new BadRequestException("Image Upload Failed");
        }
        Project projectUpdated = projectRepository.save(project);
        ProjectResponse projectResponse = projectMapper.toProjectResponse(projectUpdated);
        return ApiResponse.<ProjectResponse>builder()
                .status(200)
                .message("Update Project Successfully")
                .data(projectResponse)
                .build();
    }

    @Override
    public ApiResponse<Void> deleteProject(UUID id) {
        User user = authService.getCurrentUser();
        Project project = projectRepository.findByIdAndUser(id, user).orElseThrow(() -> new BadRequestException("Project Not Found"));
        projectRepository.delete(project);
        return ApiResponse.<Void>builder()
                .status(200)
                .message("Delete Project Successfully")
                .build();
    }
}
