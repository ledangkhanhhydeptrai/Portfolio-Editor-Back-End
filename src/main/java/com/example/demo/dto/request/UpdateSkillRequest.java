package com.example.demo.dto.request;

import com.example.demo.Enum.SkillEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateSkillRequest {
    @NotNull(message = "Category is required")
    private SkillEnum category;

    @NotNull(message = "Display order is required")
    @Min(
            value = 0,
            message = "Display order must be greater than or equal to 0"
    )
    private Integer displayOrder;

    @NotBlank(message = "Skill name is required")
    private String name;

    @NotNull(message = "Icon is required")
    @Schema(
            type = "string",
            format = "binary"
    )
    private MultipartFile iconUrl;
}
