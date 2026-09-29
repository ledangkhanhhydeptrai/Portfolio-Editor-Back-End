package com.example.demo.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateExperienceRequest {

    @NotBlank(message = "Company name is required")
    @Size(
            max = 255,
            message = "Company name must not exceed 255 characters"
    )
    private String companyName;

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

    @NotNull(message = "Start date is required")
    @PastOrPresent(
            message = "Start date cannot be in the future"
    )
    private LocalDate startDate;

    @PastOrPresent(
            message = "End date cannot be in the future"
    )
    private LocalDate endDate;

    @NotNull(message = "Current status is required")
    private Boolean isCurrent;

    @NotBlank(message = "Position is required")
    @Size(
            max = 255,
            message = "Position must not exceed 255 characters"
    )
    private String position;
}