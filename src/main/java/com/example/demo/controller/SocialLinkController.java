package com.example.demo.controller;

import com.example.demo.dto.response.SocialLinkResponse;
import com.example.demo.response.ApiResponse;
import com.example.demo.service.Interface.SocialLinkService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/social_link")
@Tag(name = "Social Link")
public class SocialLinkController {
    @Autowired
    private final SocialLinkService socialLinkService;

    public SocialLinkController(SocialLinkService socialLinkService) {
        this.socialLinkService = socialLinkService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<SocialLinkResponse>>> getAllSocialLink() {
        return ResponseEntity.ok(socialLinkService.getAllSocialLink());
    }
}
