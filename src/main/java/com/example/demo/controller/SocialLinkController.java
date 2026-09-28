package com.example.demo.controller;

import com.example.demo.dto.request.CreateSocialLinkRequest;
import com.example.demo.dto.request.UpdateSocialLinkRequest;
import com.example.demo.dto.response.SocialLinkResponse;
import com.example.demo.response.ApiResponse;
import com.example.demo.service.Interface.SocialLinkService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api")
@Tag(name = "Social Link")
public class SocialLinkController {
    @Autowired
    private final SocialLinkService socialLinkService;

    public SocialLinkController(SocialLinkService socialLinkService) {
        this.socialLinkService = socialLinkService;
    }

    @GetMapping("/public/social_link")
    public ResponseEntity<ApiResponse<List<SocialLinkResponse>>> getAllSocialLink() {
        return ResponseEntity.ok(socialLinkService.getAllSocialLink());
    }

    @GetMapping("/public/social_link/{id}")
    public ResponseEntity<ApiResponse<SocialLinkResponse>> getSocialLinkById(@PathVariable UUID id) {
        return ResponseEntity.ok(socialLinkService.getSocialLinkById(id));
    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping(value = "/create-social-link", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<SocialLinkResponse>> createSocialLink(@Valid @ModelAttribute CreateSocialLinkRequest request, @RequestPart(
            value = "iconUrl",
            required = false
    )
    MultipartFile iconUrl) {
        return ResponseEntity.ok(socialLinkService.createSocialLink(request, iconUrl));
    }

    @PreAuthorize("hasRole('USER')")
    @PutMapping(value = "/update-social-link/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<SocialLinkResponse>> updateSocialLink(@Valid @ModelAttribute UpdateSocialLinkRequest request, @PathVariable UUID id, @RequestPart(value = "iconUrl",
            required = false) MultipartFile iconUrl) {
        return ResponseEntity.ok(socialLinkService.updateSocialLink(id, request, iconUrl));
    }

    @PreAuthorize("hasRole('USER')")
    @DeleteMapping("/delete-social-link/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteSocialLink(@PathVariable UUID id) {
        return ResponseEntity.ok(socialLinkService.deleteSocialLink(id));
    }
}
