package com.example.demo.service.Interface;

import com.example.demo.dto.request.CreateSocialLinkRequest;
import com.example.demo.dto.request.UpdateSocialLinkRequest;
import com.example.demo.dto.response.SocialLinkResponse;
import com.example.demo.response.ApiResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

public interface SocialLinkService {
    ApiResponse<SocialLinkResponse> getAllSocialLink();

    ApiResponse<SocialLinkResponse> getSocialLinkById(UUID id);

    ApiResponse<SocialLinkResponse> createSocialLink(CreateSocialLinkRequest request, MultipartFile iconUrl);

    ApiResponse<SocialLinkResponse> updateSocialLink(UUID id, UpdateSocialLinkRequest request, MultipartFile iconUrl);

    ApiResponse<Void> deleteSocialLink(UUID id);

    ApiResponse<List<SocialLinkResponse>> getAllSocialLinkByUser();

    ApiResponse<SocialLinkResponse> getSocialLinkByUser(UUID id);
}
