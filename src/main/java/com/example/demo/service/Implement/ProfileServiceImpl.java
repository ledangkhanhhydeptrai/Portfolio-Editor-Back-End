package com.example.demo.service.Implement;

import com.example.demo.dto.response.ProfileResponse;
import com.example.demo.entity.Profile;
import com.example.demo.mapper.ProfileMapper;
import com.example.demo.repository.ProfileRepository;
import com.example.demo.response.ApiResponse;
import com.example.demo.service.Interface.ProfileService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProfileServiceImpl implements ProfileService {
    private final ProfileRepository profileRepository;
    private final ProfileMapper profileMapper;

    public ProfileServiceImpl(ProfileRepository profileRepository, ProfileMapper profileMapper) {
        this.profileRepository = profileRepository;
        this.profileMapper = profileMapper;
    }

    @Override
    public ApiResponse<List<ProfileResponse>> getAllProfile() {
        List<Profile> profiles = profileRepository.findAll();
        List<ProfileResponse> response = profiles.stream()
                .map(profileMapper::toProfileResponse)
                .toList();
        return ApiResponse.<List<ProfileResponse>>builder()
                .status(200)
                .message("Get All Profile Successfully")
                .data(response)
                .build();
    }

}
