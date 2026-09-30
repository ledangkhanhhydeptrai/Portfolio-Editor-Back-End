package com.example.demo.service.Interface;

import com.example.demo.dto.request.UpdateVideoRequest;
import com.example.demo.dto.request.VideoRequest;
import com.example.demo.dto.response.VideoResponse;
import com.example.demo.response.ApiResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

public interface VideoService {
    ApiResponse<List<VideoResponse>> getAllVideo();

    ApiResponse<VideoResponse>
    createVideo(
            VideoRequest request,
            MultipartFile videoFile,
            MultipartFile thumbnailFile

    );

    ApiResponse<VideoResponse> getVideoById(UUID id);

    ApiResponse<VideoResponse> updateVideoByUser(UUID id, UpdateVideoRequest request);

    ApiResponse<Void> deleteVideoByUser(UUID id);

    ApiResponse<List<VideoResponse>> getVideoByOnlyUser();

    ApiResponse<VideoResponse> getVideoByIdOnlyUser(UUID id);
}
