package com.example.demo.mapper;

import com.example.demo.dto.response.DirectionResponse;
import com.example.demo.entity.Direction;
import org.springframework.stereotype.Component;

@Component
public class DirectionMapper {

    public DirectionResponse toResponse(Direction direction) {
        return DirectionResponse.builder()
                .id(direction.getId())
                .code(direction.getCode())
                .icon(direction.getIcon())
                .title(direction.getTitle())
                .description(direction.getDescription())
                .displayOrder(direction.getDisplayOrder())
                .skills(direction.getSkills())
                .build();
    }
}