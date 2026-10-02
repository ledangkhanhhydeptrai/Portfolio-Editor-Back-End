package com.example.demo.service.Implement;

import com.example.demo.dto.request.CreateProfileRequest;
import com.example.demo.dto.request.UpdateProfileRequest;
import com.example.demo.dto.response.ProfileResponse;
import com.example.demo.entity.Profile;
import com.example.demo.entity.User;
import com.example.demo.exception.BadRequestException;
import com.example.demo.mapper.ProfileMapper;
import com.example.demo.repository.ProfileRepository;
import com.example.demo.response.ApiResponse;
import com.example.demo.service.Interface.AuthService;
import com.example.demo.service.Interface.CloudinaryService;
import com.example.demo.service.Interface.ProfileService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;

@Service
public class ProfileServiceImpl implements ProfileService {
    private final ProfileRepository profileRepository;
    private final ProfileMapper profileMapper;
    private final AuthService authService;
    private final CloudinaryService cloudinaryService;
    @Value("${portfolio.owner.email}")
    private String portfolioOwnerEmail;

    public ProfileServiceImpl(ProfileRepository profileRepository, ProfileMapper profileMapper, AuthService authService, CloudinaryService cloudinaryService) {
        this.profileRepository = profileRepository;
        this.profileMapper = profileMapper;
        this.authService = authService;
        this.cloudinaryService = cloudinaryService;
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
    public ApiResponse<ProfileResponse> getPublicProfile() {

        Profile profile = profileRepository
                .findByUser_Email(portfolioOwnerEmail)
                .orElseThrow(() ->
                        new BadRequestException("Public Profile Not Found")
                );

        ProfileResponse response =
                profileMapper.toProfileResponse(profile);

        return ApiResponse.<ProfileResponse>builder()
                .status(200)
                .message("Get Public Profile Successfully")
                .data(response)
                .build();
    }
    @Override
    public ApiResponse<ProfileResponse> getProfileByUser() {
        User user = authService.getCurrentUser();
        Profile profile = profileRepository.findByUser(user).orElseThrow(() -> new BadRequestException("Profile Not Found"));
        ProfileResponse profileResponse = profileMapper.toProfileResponse(profile);
        return ApiResponse.<ProfileResponse>builder()
                .status(200)
                .message("Get Profile Successfully")
                .data(profileResponse)
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

    @Override
    public ApiResponse<ProfileResponse> createProfile(
            CreateProfileRequest createProfileRequest,
            MultipartFile file
    ) {
        User user = authService.getCurrentUser();

        if (profileRepository.existsByUser(user)) {
            throw new BadRequestException("Profile Already Exists");
        }

        Profile profile = Profile.builder()
                .fullName(createProfileRequest.getFullName())
                .email(createProfileRequest.getEmail())
                .phone(createProfileRequest.getPhone())
                .location(createProfileRequest.getLocation())
                .jobTitle(createProfileRequest.getJobTitle())
                .shortDescription(createProfileRequest.getShortDescription())
                .aboutMe(createProfileRequest.getAboutMe())
                .cvUrl(createProfileRequest.getCvUrl())
                .user(user)
                .build();
        try {
            String avatarUrl = cloudinaryService.uploadImage(file);
            profile.setAvatarUrl(avatarUrl);
        } catch (Exception e) {
            throw new BadRequestException("Upload Image Failed");
        }
        Profile savedProfile = profileRepository.save(profile);

        ProfileResponse profileResponse =
                profileMapper.toProfileResponse(savedProfile);

        return ApiResponse.<ProfileResponse>builder()
                .status(201)
                .message("Create Profile Successfully")
                .data(profileResponse)
                .build();
    }
}
