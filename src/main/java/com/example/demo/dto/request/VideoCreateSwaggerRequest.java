package com.example.demo.dto.request;

import com.example.demo.Enum.VideoEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VideoCreateSwaggerRequest {

    @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
    private String title;

    @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
    private String description;

    @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
    private VideoEnum category;

    @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer year;

    @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer displayOrder;

    @Schema(
            type = "string",
            format = "binary"
    )
    private MultipartFile videoFile;

    @Schema(
            type = "string",
            format = "binary"
    )
    private MultipartFile thumbnailFile;
}