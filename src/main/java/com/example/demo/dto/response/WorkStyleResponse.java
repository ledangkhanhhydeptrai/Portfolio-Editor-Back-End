package com.example.demo.dto.response;

import lombok.*;

import java.util.UUID;

@Data
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class WorkStyleResponse {

    private UUID id;

    private String title;

    private String description;

    private Integer displayOrder;
}