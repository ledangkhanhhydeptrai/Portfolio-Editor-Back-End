package com.example.demo.dto.response;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class SkillResponse {
    private UUID id;
    private String name;
    private String category;
    private String iconUrl;
    private Integer displayOrder;
}
