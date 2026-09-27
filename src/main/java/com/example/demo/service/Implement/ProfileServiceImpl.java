package com.example.demo.service.Implement;

import com.example.demo.dto.request.UpdateProfileRequest;
import com.example.demo.dto.response.ProfileResponse;
import com.example.demo.entity.Profile;
import com.example.demo.entity.User;
import com.example.demo.exception.BadRequestException;
import com.example.demo.mapper.ProfileMapper;
import com.example.demo.repository.ProfileRepository;
import com.example.demo.response.ApiResponse;
import com.example.demo.service.Interface.AuthService;
import com.example.demo.service.Interface.ProfileService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ProfileServiceImpl implements ProfileService {
    private final ProfileRepository profileRepository;
    private final ProfileMapper profileMapper;
    private final AuthService authService;

    public ProfileServiceImpl(ProfileRepository profileRepository, ProfileMapper profileMapper, AuthService authService) {
        this.profileRepository = profileRepository;
        this.profileMapper = profileMapper;
        this.authService = authService;
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

    @Override
    public ApiResponse<ProfileResponse> updateProfileByUser(UpdateProfileRequest updateProfileRequest) {
        User user = authService.getCurrentUser();
        Profile profile = profileRepository.findByUser(user)
                .orElseThrow(() -> new BadRequestException("User Not Found"));
        profile.setJobTitle(updateProfileRequest.getJobTitle());
        profile.setFullName(updateProfileRequest.getFullName());
        profile.setShortDescription(updateProfileRequest.getShortDescription());
        profile.setAboutMe(updateProfileRequest.getAboutMe());
        profile.setEmail(updateProfileRequest.getEmail());
        profile.setPhone(updateProfileRequest.getPhone());
        profile.setLocation(updateProfileRequest.getLocation());
        Profile savedProfile = profileRepository.save(profile);
        ProfileResponse profileResponse = profileMapper.toProfileResponse(savedProfile);
        return ApiResponse.<ProfileResponse>builder()
                .status(200)
                .message("Update Profile Successfully")
                .data(profileResponse)
                .build();
    }
}
