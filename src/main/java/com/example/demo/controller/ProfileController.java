package com.example.demo.controller;

import com.example.demo.dto.request.UpdateProfileRequest;
import com.example.demo.dto.response.ProfileResponse;
import com.example.demo.response.ApiResponse;
import com.example.demo.service.Interface.ProfileService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/public/profile")
    public ResponseEntity<ApiResponse<List<ProfileResponse>>> getAllProfile() {
        return ResponseEntity.ok(profileService.getAllProfile());
    }

    @PutMapping("/profile")
    public ResponseEntity<ApiResponse<ProfileResponse>> updateProfile(@RequestBody UpdateProfileRequest updateProfileRequest) {
        return ResponseEntity.ok(profileService.updateProfileByUser(updateProfileRequest));
    }
}
