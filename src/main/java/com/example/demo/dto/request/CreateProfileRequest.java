package com.example.demo.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateProfileRequest {

    private String fullName;

    private String email;

    private String phone;

    private String location;

    private String jobTitle;

    private String shortDescription;

    private String aboutMe;

    private String cvUrl;

    @Schema(
            type = "string",
            format = "binary"
    )
    private MultipartFile avatarUrl;
    private String workDirection;
    private String availabilityStatus;
    private String quote;
}
