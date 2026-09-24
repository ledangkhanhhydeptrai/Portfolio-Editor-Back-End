package com.example.demo.mapper;

import com.example.demo.dto.response.EducationResponse;
import com.example.demo.entity.Education;
import org.springframework.stereotype.Component;

@Component
public class EducationMapper {
    public EducationResponse toEducationResponse(Education education) {
        if (education == null) {
            return null;
        }
        return EducationResponse.builder()
                .id(education.getId())
                .schoolName(education.getSchoolName())
                .major(education.getMajor())
                .description(education.getDescription())
                .degree(education.getDegree())
                .startYear(education.getStartYear())
                .endYear(education.getEndYear())
                .displayOrder(education.getDisplayOrder())
                .build();
    }
}
