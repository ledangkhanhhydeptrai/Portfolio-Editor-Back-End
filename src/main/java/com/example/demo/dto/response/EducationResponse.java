package com.example.demo.dto.response;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class EducationResponse {
    private UUID id;
    private String schoolName;
    private String major;
    private String degree;
    private Integer startYear;
    private Integer endYear;
    private String description;
    private Integer displayOrder;
}
