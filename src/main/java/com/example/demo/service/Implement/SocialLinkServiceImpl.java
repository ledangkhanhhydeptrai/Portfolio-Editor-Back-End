package com.example.demo.service.Implement;

import com.example.demo.dto.request.CreateSocialLinkRequest;
import com.example.demo.dto.request.UpdateSocialLinkRequest;
import com.example.demo.dto.response.SocialLinkResponse;
import com.example.demo.entity.SocialLink;
import com.example.demo.entity.User;
import com.example.demo.exception.BadRequestException;
import com.example.demo.mapper.SocialLinkMapper;
import com.example.demo.repository.SocialLinkRepository;
import com.example.demo.response.ApiResponse;
import com.example.demo.service.Interface.AuthService;
import com.example.demo.service.Interface.CloudinaryService;
import com.example.demo.service.Interface.SocialLinkService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class SocialLinkServiceImpl implements SocialLinkService {
    private final SocialLinkRepository socialLinkRepository;
    private final SocialLinkMapper socialLinkMapper;
    private final AuthService authService;
    private final CloudinaryService cloudinaryService;

    public SocialLinkServiceImpl(SocialLinkRepository socialLinkRepository, SocialLinkMapper socialLinkMapper, AuthService authService, CloudinaryService cloudinaryService) {
        this.socialLinkRepository = socialLinkRepository;
        this.socialLinkMapper = socialLinkMapper;
        this.authService = authService;
        this.cloudinaryService = cloudinaryService;
    }

    @Override
    public ApiResponse<List<SocialLinkResponse>> getAllSocialLink() {
        List<SocialLink> socialLinks = socialLinkRepository.findAllByOrderByDisplayOrderAsc();
        List<SocialLinkResponse> socialLinkResponses = socialLinks.stream()
                .map(socialLinkMapper::toSocialLinkResponse)
                .toList();
        return ApiResponse.<List<SocialLinkResponse>>builder()
                .status(200)
                .message("Get All Social Link Successfully")
                .data(socialLinkResponses)
                .build();
    }

    @Override
    public ApiResponse<List<SocialLinkResponse>> getAllSocialLinkByUser() {
        User user = authService.getCurrentUser();
        List<SocialLink> socialLinks = socialLinkRepository.findAllByUserOrderByDisplayOrderAsc(user);
        List<SocialLinkResponse> socialLinkResponses = socialLinks.stream()
                .map(socialLinkMapper::toSocialLinkResponse)
                .toList();
        return ApiResponse.<List<SocialLinkResponse>>builder()
                .status(200)
                .message("Get All Social Link Successfully")
                .data(socialLinkResponses)
                .build();
    }

    @Override
    public ApiResponse<SocialLinkResponse> getSocialLinkByUser(UUID id) {
        User user = authService.getCurrentUser();
        SocialLink socialLinks = socialLinkRepository.findByIdAndUser(id, user)
                .orElseThrow(() -> new BadRequestException("Social Link User By Id not found"));
        SocialLinkResponse socialLinkResponses = socialLinkMapper.toSocialLinkResponse(socialLinks);
        return ApiResponse.<SocialLinkResponse>builder()
                .status(200)
                .message("Get Social Link Successfully")
                .data(socialLinkResponses)
                .build();
    }

    @Override
    public ApiResponse<SocialLinkResponse> getSocialLinkById(UUID id) {
        SocialLink socialLink = socialLinkRepository.findById(id).orElseThrow(() -> new BadRequestException("Social Link Not Found"));
        SocialLinkResponse response = socialLinkMapper.toSocialLinkResponse(socialLink);
        return ApiResponse.<SocialLinkResponse>builder()
                .status(200)
                .message("Get Social Link Successfully")
                .data(response)
                .build();
    }

    @Override
    public ApiResponse<SocialLinkResponse> createSocialLink(CreateSocialLinkRequest request, MultipartFile iconUrl) {
        if (iconUrl == null || iconUrl.isEmpty()) {
            throw new BadRequestException("Icon Url is required");
        }
        User user = authService.getCurrentUser();
        SocialLink socialLink = new SocialLink();
        socialLink.setUser(user);
        socialLink.setPlatform(request.getPlatform());
        socialLink.setDisplayOrder(request.getDisplayOrder());
        socialLink.setUrl(request.getUrl());
        try {
            String image = cloudinaryService.uploadFile(iconUrl);
            socialLink.setIconUrl(image);
        } catch (Exception e) {
            throw new RuntimeException(
                    "Upload SocialLinkUrl Error",
                    e
            );
        }
        SocialLink savedSocialLink = socialLinkRepository.save(socialLink);
        SocialLinkResponse response = socialLinkMapper.toSocialLinkResponse(savedSocialLink);
        return ApiResponse.<SocialLinkResponse>builder()
                .status(200)
                .message("Create Social Link Successfully")
                .data(response)
                .build();
    }

    @Override
    public ApiResponse<SocialLinkResponse> updateSocialLink(UUID id, UpdateSocialLinkRequest request, MultipartFile iconUrl) {
        SocialLink socialLink = socialLinkRepository.findById(id)
                .orElseThrow(() -> new BadRequestException("Social Link Not Found"));
        User user = authService.getCurrentUser();
        socialLink.setUser(user);
        socialLink.setDisplayOrder(request.getDisplayOrder());
        socialLink.setPlatform(request.getPlatform());
        socialLink.setUrl(request.getUrl());
        try {
            String image = cloudinaryService.uploadFile(iconUrl);
            socialLink.setIconUrl(image);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        SocialLink savedSocialLink = socialLinkRepository.save(socialLink);
        SocialLinkResponse response = socialLinkMapper.toSocialLinkResponse(savedSocialLink);
        return ApiResponse.<SocialLinkResponse>builder()
                .status(200)
                .message("Update Social Link Successfully")
                .data(response)
                .build();
    }

    @Override
    public ApiResponse<Void> deleteSocialLink(UUID id) {
        SocialLink socialLink = socialLinkRepository.findById(id).orElseThrow(() -> new BadRequestException("Social Link Not Found"));
        socialLinkRepository.delete(socialLink);
        return ApiResponse.<Void>builder()
                .status(200)
                .message("Delete Social Link Successfully")
                .build();
    }
}
