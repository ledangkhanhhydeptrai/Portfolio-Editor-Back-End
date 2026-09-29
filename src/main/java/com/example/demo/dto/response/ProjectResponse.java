package com.example.demo.dto.response;

import com.example.demo.Enum.SkillEnum;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class ProjectResponse {
    private UUID id;
    private String title;
    private String description;
    private String thumbnailUrl;
    private String githubUrl;
    private String demoUrl;
    private Integer displayOrder;
    private Boolean featured;
    private SkillEnum category;
}
