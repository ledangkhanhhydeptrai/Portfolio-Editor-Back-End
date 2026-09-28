package com.example.demo.service.Implement;

import com.example.demo.dto.request.UpdateVideoRequest;
import com.example.demo.dto.request.VideoRequest;
import com.example.demo.dto.response.CloudinaryVideoResponse;
import com.example.demo.dto.response.VideoResponse;
import com.example.demo.entity.User;
import com.example.demo.entity.Video;
import com.example.demo.exception.BadRequestException;
import com.example.demo.mapper.VideoMapper;
import com.example.demo.repository.VideoRepository;
import com.example.demo.response.ApiResponse;
import com.example.demo.service.Interface.AuthService;
import com.example.demo.service.Interface.CloudinaryService;
import com.example.demo.service.Interface.VideoService;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@Service
public class VideoServiceImpl
        implements VideoService {

    private final VideoRepository videoRepository;
    private final VideoMapper videoMapper;
    private final AuthService authService;
    private final CloudinaryService cloudinaryService;

    public VideoServiceImpl(
            VideoRepository videoRepository,
            VideoMapper videoMapper,
            CloudinaryService cloudinaryService,
            AuthService authService
    ) {
        this.videoRepository =
                videoRepository;

        this.videoMapper =
                videoMapper;

        this.cloudinaryService =
                cloudinaryService;

        this.authService =
                authService;
    }

    @Override
    public ApiResponse<List<VideoResponse>>
    getAllVideo() {

        List<Video> videos =
                videoRepository.findAll();

        List<VideoResponse> responses =
                videos.stream()
                        .map(
                                videoMapper::toVideoResponse
                        )
                        .toList();

        return ApiResponse
                .<List<VideoResponse>>builder()
                .status(200)
                .message(
                        "Get All Video Successfully"
                )
                .data(responses)
                .build();
    }

    @Override
    public ApiResponse<VideoResponse> createVideo(
            VideoRequest request,
            MultipartFile videoFile,
            MultipartFile thumbnailFile
    ) {

        if (videoFile == null || videoFile.isEmpty()) {
            throw new BadRequestException(
                    "Video file is required"
            );
        }

        if (thumbnailFile == null || thumbnailFile.isEmpty()) {
            throw new BadRequestException(
                    "Thumbnail file is required"
            );
        }

        User user =
                authService.getCurrentUser();

        Video video =
                new Video();

        video.setCategory(
                request.getCategory()
        );

        video.setDescription(
                request.getDescription()
        );

        video.setDisplayOrder(
                request.getDisplayOrder()
        );

        video.setTitle(
                request.getTitle()
        );

        video.setYear(
                request.getYear()
        );

        video.setUser(
                user
        );

        try {

            CloudinaryVideoResponse upload =
                    cloudinaryService.uploadVideo(
                            videoFile
                    );

            video.setVideoUrl(
                    upload.getUrl()
            );

            video.setDuration(
                    formatDuration(
                            upload.getDuration()
                    )
            );

        } catch (Exception e) {

            throw new RuntimeException(
                    "Upload Video Error",
                    e
            );
        }

        try {

            String thumbnailUrl =
                    cloudinaryService.uploadFile(
                            thumbnailFile
                    );

            video.setThumbnailUrl(
                    thumbnailUrl
            );

        } catch (Exception e) {

            throw new RuntimeException(
                    "Upload Thumbnail Error",
                    e
            );
        }

        Video saved =
                videoRepository.save(
                        video
                );

        VideoResponse response =
                videoMapper.toVideoResponse(
                        saved
                );

        return ApiResponse
                .<VideoResponse>builder()
                .status(200)
                .message(
                        "Create Video Successfully"
                )
                .data(response)
                .build();
    }


    private String formatDuration(
            Double duration
    ) {

        if (duration == null) {
            return null;
        }

        long totalSeconds =
                Math.round(
                        duration
                );

        long minutes =
                totalSeconds / 60;

        long seconds =
                totalSeconds % 60;

        return String.format(
                "%02d:%02d",
                minutes,
                seconds
        );
    }

    @Override
    public ApiResponse<VideoResponse> getVideoById(UUID id) {
        Video video = videoRepository.findById(id)
                .orElseThrow(() -> new BadRequestException("Video does not exist"));
        VideoResponse response = videoMapper.toVideoResponse(video);
        return ApiResponse.<VideoResponse>builder()
                .status(200)
                .message("Get Video By Id Successfully")
                .data(response)
                .build();
    }

    @Override
    public ApiResponse<VideoResponse> updateVideoByUser(UUID id, UpdateVideoRequest request) {
        Video video = videoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Video does not exist"));
        video.setTitle(request.getTitle());
        video.setDescription(request.getDescription());
        video.setYear(request.getYear());
        video.setDisplayOrder(request.getDisplayOrder());
        video.setCategory(request.getCategory());
        Video saved = videoRepository.save(video);
        VideoResponse response = videoMapper.toVideoResponse(saved);
        return ApiResponse.<VideoResponse>builder()
                .status(200)
                .message("Update Video Successfully")
                .data(response)
                .build();
    }

    @Override
    public ApiResponse<Void> deleteVideoByUser(UUID id) {
        Video video = videoRepository.findById(id)
                .orElseThrow(() -> new BadRequestException("Video does not exist"));
        videoRepository.delete(video);
        return ApiResponse.<Void>builder()
                .status(200)
                .message("Delete Video Successfully")
                .build();
    }
}