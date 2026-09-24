package com.example.demo.mapper;

import com.example.demo.dto.response.ProfileResponse;
import com.example.demo.entity.Profile;
import org.springframework.stereotype.Component;

@Component
public class ProfileMapper {
    public ProfileResponse toProfileResponse(Profile profile) {
        if (profile == null) {
            return null;
        }
        return ProfileResponse.builder()
                .id(profile.getId())
                .aboutMe(profile.getAboutMe())
                .email(profile.getEmail())
                .phone(profile.getPhone())
                .cvUrl(profile.getCvUrl())
                .avatarUrl(profile.getAvatarUrl())
                .fullName(profile.getFullName())
                .jobTitle(profile.getJobTitle())
                .location(profile.getLocation())
                .shortDescription(profile.getShortDescription())
                .build();
    }
}
