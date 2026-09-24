package com.example.demo.controller;

import com.example.demo.dto.response.ProjectResponse;
import com.example.demo.entity.Project;
import com.example.demo.response.ApiResponse;
import com.example.demo.service.Interface.ProjectService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

@RestController
@RequestMapping("/api/projects")
@Tag(name = "Project")
public class ProjectController {
    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<ProjectResponse>>> getAllProject() {
        return ResponseEntity.ok(projectService.getAllProject());
    }
}
