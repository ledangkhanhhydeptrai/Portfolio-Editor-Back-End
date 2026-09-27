package com.example.demo.dto.request;

import lombok.*;

@Data
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateProfileRequest {
    private String fullName;

    private String jobTitle;

    private String shortDescription;

    private String aboutMe;

    private String email;

    private String phone;

    private String location;
}
