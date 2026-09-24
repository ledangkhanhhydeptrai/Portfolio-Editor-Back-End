package com.example.demo.mapper;

import com.example.demo.dto.response.ExperienceResponse;
import com.example.demo.dto.response.SkillResponse;
import com.example.demo.entity.Experience;
import org.springframework.stereotype.Component;

@Component
public class ExperienceMapper {
    public ExperienceResponse toResponse(Experience experience) {
        if(experience == null){
            return null;
        }
        return ExperienceResponse.builder()
                .id(experience.getId())
                .companyName(experience.getCompanyName())
                .description(experience.getDescription())
                .position(experience.getPosition())
                .startDate(experience.getStartDate())
                .endDate(experience.getEndDate())
                .isCurrent(experience.getIsCurrent())
                .displayOrder(experience.getDisplayOrder())
                .build();
    }
}
