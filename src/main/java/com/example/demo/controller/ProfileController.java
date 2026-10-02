package com.example.demo.controller;

import com.example.demo.dto.request.CreateProfileRequest;
import com.example.demo.dto.request.UpdateProfileRequest;
import com.example.demo.dto.response.ProfileResponse;
import com.example.demo.response.ApiResponse;
import com.example.demo.service.Interface.ProfileService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api")
@Tag(name = "Profile")
public class ProfileController {
    @Autowired
    private final ProfileService profileService;

    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    @GetMapping("/admin/profile")
    public ResponseEntity<ApiResponse<List<ProfileResponse>>> getAllProfile() {
        return ResponseEntity.ok(profileService.getAllProfile());
    }
    @GetMapping("/public/profile")
    public ResponseEntity<ApiResponse<ProfileResponse>> getPublicProfile() {
        return ResponseEntity.ok(
                profileService.getPublicProfile()
        );
    }
    @PostMapping(
            value = "/user/profile",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public ResponseEntity<ApiResponse<ProfileResponse>> createProfile(
            @ModelAttribute CreateProfileRequest createProfileRequest, @RequestPart(value = "avatarUrl", required = true) MultipartFile file
    ) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(profileService.createProfile(createProfileRequest, file));
    }

    @GetMapping("/user/profile")
    public ResponseEntity<ApiResponse<ProfileResponse>> getProfileByUser() {
        return ResponseEntity.ok(profileService.getProfileByUser());
    }

    @PutMapping("/profile")
    public ResponseEntity<ApiResponse<ProfileResponse>> updateProfile(@RequestBody UpdateProfileRequest updateProfileRequest) {
        return ResponseEntity.ok(profileService.updateProfileByUser(updateProfileRequest));
    }
}
