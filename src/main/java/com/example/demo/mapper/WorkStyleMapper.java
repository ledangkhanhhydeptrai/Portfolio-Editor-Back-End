package com.example.demo.mapper;

import com.example.demo.dto.response.WorkStyleResponse;
import com.example.demo.entity.WorkStyle;
import org.springframework.stereotype.Component;

@Component
public class WorkStyleMapper {

    public WorkStyleResponse toResponse(
            WorkStyle workStyle
    ) {
        return WorkStyleResponse.builder()
                .id(workStyle.getId())
                .title(workStyle.getTitle())
                .description(workStyle.getDescription())
                .displayOrder(workStyle.getDisplayOrder())
                .build();
    }
}