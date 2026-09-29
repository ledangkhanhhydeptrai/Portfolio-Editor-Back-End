package com.example.demo.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateCVRequest {

    @NotBlank(message = "Title is required")
    @Size(
            max = 255,
            message = "Title must not exceed 255 characters"
    )
    private String title;

    @NotNull(message = "Display order is required")
    @Min(
            value = 0,
            message = "Display order must be greater than or equal to 0"
    )
    private Integer displayOrder;

    @NotNull(message = "Primary status is required")
    private Boolean isPrimary;

    @NotNull(message = "CV file is required")
    @Schema(
            type = "string",
            format = "binary"
    )
    private MultipartFile fileUrl;
}