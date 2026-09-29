package com.example.demo.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateEducationRequest {

    @NotBlank(message = "Degree is required")
    @Size(
            max = 255,
            message = "Degree must not exceed 255 characters"
    )
    private String degree;

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

    @NotNull(message = "Start year is required")
    @Min(
            value = 1900,
            message = "Start year must be greater than or equal to 1900"
    )
    @Max(
            value = 2100,
            message = "Start year must not exceed 2100"
    )
    private Integer startYear;

    @NotNull(message = "End year is required")
    @Min(
            value = 1900,
            message = "End year must be greater than or equal to 1900"
    )
    @Max(
            value = 2100,
            message = "End year must not exceed 2100"
    )
    private Integer endYear;

    @NotBlank(message = "Major is required")
    @Size(
            max = 255,
            message = "Major must not exceed 255 characters"
    )
    private String major;

    @NotBlank(message = "School name is required")
    @Size(
            max = 255,
            message = "School name must not exceed 255 characters"
    )
    private String schoolName;
}