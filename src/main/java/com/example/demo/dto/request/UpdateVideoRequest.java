package com.example.demo.dto.request;

import com.example.demo.Enum.VideoEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateVideoRequest {

    @NotBlank(message = "Title is required")
    @Pattern(
            regexp = "^(?!\\s*(?i:string)\\s*$).+",
            message = "Title is invalid"
    )
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
    private String title;

    @NotBlank(message = "Description is required")
    @Pattern(
            regexp = "^(?!\\s*(?i:string)\\s*$).+",
            message = "Description is invalid"
    )
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
    private String description;

    @NotNull(message = "Category is required")
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
    private VideoEnum category;

    @NotNull(message = "Year is required")
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer year;

    @NotNull(message = "Display order is required")
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer displayOrder;
}