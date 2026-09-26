package com.example.demo.dto.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProfileRequest {

    private String fullName;

    private String jobTitle;

    private String shortDescription;

    private String aboutMe;

    private String email;

    private String phone;

    private String location;
}