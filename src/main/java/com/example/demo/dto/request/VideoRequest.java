package com.example.demo.dto.request;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VideoRequest {
    private String title;
    private String description;
    private String category;
    private Integer year;
    private Integer displayOrder;
    private MultipartFile videoFile;
    private MultipartFile thumbnailFile;
}