package com.example.demo.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Data
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateSocialLinkRequest {
    @NotBlank
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
    private String platform;
    @NotNull(message = "Display order is required")
    @Min(
            value = 0,
            message = "Display order must be greater than or equal to 0"
    )
    private Integer displayOrder;
    @Schema(
            type = "string",
            format = "binary"
    )
    private MultipartFile iconUrl;
    @NotBlank(message = "URL is required")
    private String url;
}
