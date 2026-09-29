package com.example.demo.dto.request;

import com.example.demo.Enum.SkillEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CreateProjectRequest {

    @NotNull(message = "Category is required")
    private SkillEnum category;

    @Pattern(
            regexp = "^(https?://.+)?$",
            message = "Demo URL must be a valid HTTP or HTTPS URL"
    )
    private String demoUrl;

    @NotBlank(message = "Description is required")
    @Size(
            max = 5000,
            message = "Description must not exceed 5000 characters"
    )
    private String description;

    @NotNull(message = "Display order is required")
    @Min(
            value = 0,
            message = "Display order must be greater than or equal to 0"
    )
    private Integer displayOrder;

    @NotNull(message = "Featured is required")
    private Boolean featured;

    @Pattern(
            regexp = "^(https?://.+)?$",
            message = "GitHub URL must be a valid HTTP or HTTPS URL"
    )
    private String githubUrl;

    @NotBlank(message = "Title is required")
    @Size(
            max = 255,
            message = "Title must not exceed 255 characters"
    )
    private String title;

    @NotNull(message = "Thumbnail is required")
    @Schema(
            type = "string",
            format = "binary"
    )
    private MultipartFile thumbnailUrl;
}