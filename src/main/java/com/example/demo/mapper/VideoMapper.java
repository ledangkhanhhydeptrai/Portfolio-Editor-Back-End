package com.example.demo.mapper;

import com.example.demo.dto.response.VideoResponse;
import com.example.demo.entity.SocialLink;
import com.example.demo.entity.Video;
import org.springframework.stereotype.Component;

@Component
public class VideoMapper {
    public VideoResponse toVideoResponse(Video video) {
        if (video == null) return null;
        return VideoResponse.builder()
                .id(video.getId())
                .category(video.getCategory())
                .description(video.getDescription())
                .videoUrl(video.getVideoUrl())
                .thumbnailUrl(video.getThumbnailUrl())
                .displayOrder(video.getDisplayOrder())
                .duration(video.getDuration())
                .title(video.getTitle())
                .year(video.getYear())
                .build();
    }
}
