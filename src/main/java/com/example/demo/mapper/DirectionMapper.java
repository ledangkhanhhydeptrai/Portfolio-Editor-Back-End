package com.example.demo.mapper;

import com.example.demo.dto.response.DirectionResponse;
import com.example.demo.dto.response.SkillResponse;
import com.example.demo.entity.Direction;
import com.example.demo.entity.Skill;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DirectionMapper {

    public DirectionResponse toResponse(Direction direction) {

        List<SkillResponse> skills = direction.getSkills()
                .stream()
                .map(this::toSkillResponse)
                .toList();

        return DirectionResponse.builder()
                .id(direction.getId())
                .code(direction.getCode())
                .icon(direction.getIcon())
                .title(direction.getTitle())
                .description(direction.getDescription())
                .displayOrder(direction.getDisplayOrder())
                .skills(skills)
                .build();
    }

    private SkillResponse toSkillResponse(Skill skill) {
        return SkillResponse.builder()
                .id(skill.getId())
                .name(skill.getName())
                .category(skill.getCategory())
                .iconUrl(skill.getIconUrl())
                .displayOrder(skill.getDisplayOrder())
                .build();
    }
}