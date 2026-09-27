package com.example.demo.dto.response;

import com.example.demo.Enum.VideoEnum;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VideoResponse {

    private UUID id;

    private String title;

    private String description;

    private String videoUrl;

    private String thumbnailUrl;

    private VideoEnum category;

    private String duration;

    private Integer year;

    private Integer displayOrder;
}