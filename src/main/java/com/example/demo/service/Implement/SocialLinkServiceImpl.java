package com.example.demo.service.Implement;

import com.example.demo.dto.response.SocialLinkResponse;
import com.example.demo.entity.SocialLink;
import com.example.demo.mapper.SocialLinkMapper;
import com.example.demo.repository.SocialLinkRepository;
import com.example.demo.response.ApiResponse;
import com.example.demo.service.Interface.SocialLinkService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SocialLinkServiceImpl implements SocialLinkService {
    private final SocialLinkRepository socialLinkRepository;
    private final SocialLinkMapper socialLinkMapper;

    public SocialLinkServiceImpl(SocialLinkRepository socialLinkRepository, SocialLinkMapper socialLinkMapper) {
        this.socialLinkRepository = socialLinkRepository;
        this.socialLinkMapper = socialLinkMapper;
    }

    @Override
    public ApiResponse<List<SocialLinkResponse>> getAllSocialLink() {
        List<SocialLink> socialLinks = socialLinkRepository.findAll();
        List<SocialLinkResponse> socialLinkResponses = socialLinks.stream()
                .map(socialLinkMapper::toSocialLinkResponse)
                .toList();
        return ApiResponse.<List<SocialLinkResponse>>builder()
                .status(200)
                .message("Get All Social Link Successfully")
                .data(socialLinkResponses)
                .build();
    }
}
