package com.example.demo.dto.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Data
@Builder
public class ExperienceResponse {
    private UUID id;
    private String companyName;
    private String position;
    private String description;
    private LocalDate startDate;
    private LocalDate endDate;
    private Boolean isCurrent;
    private Integer displayOrder;
}
