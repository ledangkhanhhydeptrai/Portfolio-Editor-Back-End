package com.example.demo.dto.response;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class SocialLinkResponse {
    private UUID id;
    private String platform;
    private String url;
    private String iconUrl;
    private Integer displayOrder;
}

