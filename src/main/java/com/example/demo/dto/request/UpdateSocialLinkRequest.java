package com.example.demo.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UpdateSocialLinkRequest {
    private String platform;
    private Integer displayOrder;
    private String url;
    @Schema(
            type = "string",
            format = "binary"
    )
    private MultipartFile iconUrl;
}
