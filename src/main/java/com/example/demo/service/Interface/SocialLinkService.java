package com.example.demo.service.Interface;

import com.example.demo.dto.response.SocialLinkResponse;
import com.example.demo.response.ApiResponse;

import java.util.List;

public interface SocialLinkService {
    ApiResponse<List<SocialLinkResponse>> getAllSocialLink();
}
