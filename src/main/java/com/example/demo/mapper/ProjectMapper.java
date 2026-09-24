package com.example.demo.mapper;

import com.example.demo.dto.response.ProjectResponse;
import com.example.demo.entity.Project;
import org.springframework.stereotype.Component;

@Component
public class ProjectMapper {
    public ProjectResponse toProjectResponse(Project project) {
        if (project == null) {
            return null;
        }
        return ProjectResponse.builder()
                .id(project.getId())
                .title(project.getTitle())
                .description(project.getDescription())
                .thumbnailUrl(project.getThumbnailUrl())
                .githubUrl(project.getGithubUrl())
                .demoUrl(project.getDemoUrl())
                .displayOrder(project.getDisplayOrder())
                .featured(project.getFeatured())
                .category(project.getCategory())
                .build();
    }
}
