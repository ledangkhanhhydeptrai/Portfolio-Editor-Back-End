package com.example.demo.dto.response;

import lombok.*;

import java.util.List;
import java.util.UUID;

@Data
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DirectionResponse {

    private UUID id;

    private String code;

    private String icon;

    private String title;

    private String description;

    private Integer displayOrder;

    private List<String> skills;
}