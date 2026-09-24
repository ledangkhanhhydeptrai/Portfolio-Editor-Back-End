package com.example.demo.mapper;

import com.example.demo.dto.response.SkillResponse;
import com.example.demo.entity.Skill;
import org.springframework.stereotype.Component;

@Component
public class SkillMapper {
    public SkillResponse toSkillResponse(Skill skill) {
        if (skill == null) {
            return null;
        }
        return SkillResponse.builder()
                .id(skill.getId())
                .name(skill.getName())
                .category(skill.getCategory())
                .iconUrl(skill.getIconUrl())
                .displayOrder(skill.getDisplayOrder())
                .build();
    }
}
