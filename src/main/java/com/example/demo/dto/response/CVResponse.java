package com.example.demo.dto.response;

import lombok.*;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CVResponse {

    private UUID id;

    private String title;

    private String fileUrl;

    private Integer displayOrder;

    private Boolean isPrimary;
}