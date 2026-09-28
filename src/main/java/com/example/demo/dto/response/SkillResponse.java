package com.example.demo.dto.response;

import com.example.demo.Enum.SkillEnum;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class SkillResponse {
    private UUID id;
    private String name;
    private SkillEnum category;
    private String iconUrl;
    private Integer displayOrder;
}
