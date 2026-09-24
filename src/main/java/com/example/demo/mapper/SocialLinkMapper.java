package com.example.demo.mapper;

import com.example.demo.dto.response.SocialLinkResponse;
import com.example.demo.entity.SocialLink;
import org.springframework.stereotype.Component;

@Component
public class SocialLinkMapper {
    public SocialLinkResponse toSocialLinkResponse(SocialLink socialLink) {
        if (socialLink == null) {
            return null;
        }
        return SocialLinkResponse.builder()
                .id(socialLink.getId())
                .url(socialLink.getUrl())
                .iconUrl(socialLink.getIconUrl())
                .displayOrder(socialLink.getDisplayOrder())
                .platform(socialLink.getPlatform())
                .build();
    }
}
