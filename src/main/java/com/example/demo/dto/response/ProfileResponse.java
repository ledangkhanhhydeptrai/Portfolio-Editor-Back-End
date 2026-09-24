package com.example.demo.dto.response;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class ProfileResponse {
    private UUID id;
    private String fullName;
    private String jobTitle;
    private String shortDescription;
    private String aboutMe;
    private String avatarUrl;
    private String cvUrl;
    private String email;
    private String phone;
    private String location;
}
