package com.example.demo.controller;

import com.example.demo.dto.request.CreateProjectRequest;
import com.example.demo.dto.response.ProjectResponse;
import com.example.demo.response.ApiResponse;
import com.example.demo.service.Interface.ProjectService;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api")
@Tag(name = "Project")
public class ProjectController {
    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @GetMapping("/public/projects")
    public ResponseEntity<ApiResponse<ProjectResponse>> getAllProject() {
        return ResponseEntity.ok(projectService.getAllProject());
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/user/project")
    public ResponseEntity<ApiResponse<List<ProjectResponse>>> getAllProjectByUser() {
        return ResponseEntity.ok(projectService.getAllProjectByUser());
    }

    @GetMapping("/public/projects/{id}")
    public ResponseEntity<ApiResponse<ProjectResponse>> getProjectById(@PathVariable UUID id) {
        return ResponseEntity.ok(projectService.getProjectById(id));
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/user/project/{id}")
    public ResponseEntity<ApiResponse<ProjectResponse>> getProjectByIdAndUser(@PathVariable UUID id) {
        return ResponseEntity.ok(projectService.getProjectByIdAndUser(id));
    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping(value = "/create-project", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<ProjectResponse>> createProject(@Valid @ModelAttribute CreateProjectRequest request, @RequestPart(value = "thumbnailUrl", required = false) MultipartFile thumbnailUrl) {
        return ResponseEntity.ok(projectService.createProject(request, thumbnailUrl));
    }

    @PreAuthorize("hasRole('USER')")
    @PutMapping(value = "/update-project/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<ProjectResponse>> updateProject(@PathVariable UUID id, @Valid @ModelAttribute CreateProjectRequest request, @RequestPart(value = "thumbnailUrl", required = false) MultipartFile thumbnailUrl) {
        return ResponseEntity.ok(projectService.updateProject(id, request, thumbnailUrl));
    }

    @PreAuthorize("hasRole('USER')")
    @DeleteMapping("/delete-project/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteProject(@PathVariable UUID id) {
        return ResponseEntity.ok(projectService.deleteProject(id));
    }
}
