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

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@Service
public class VideoServiceImpl
        implements VideoService {
    @Value("${portfolio.owner.email}")
    private String portfolioOwnerEmail;

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
    public ApiResponse<VideoResponse>
    getAllVideo() {

        Video video = videoRepository
                .findFirstByUser_EmailOrderByDisplayOrderAsc(
                        portfolioOwnerEmail
                )
                .orElseThrow(() -> new BadRequestException("Public Video Not Found"));

        VideoResponse response = videoMapper.toVideoResponse(video);

        return ApiResponse.<VideoResponse>builder()
                .status(200)
                .message("Get Public Video Successfully")
                .data(response)
                .build();
    }

    @Override
    public ApiResponse<VideoResponse> createVideo(
            VideoRequest request,
            MultipartFile videoFile,
            MultipartFile thumbnailFile
    ) {
        System.out.println("\n========== 1. START CREATE VIDEO ==========");

        System.out.println("Title: " + request.getTitle());
        System.out.println("Description: " + request.getDescription());
        System.out.println("Category: " + request.getCategory());
        System.out.println("Display Order: " + request.getDisplayOrder());
        System.out.println("Year: " + request.getYear());

        System.out.println("Video File: " +
                (videoFile != null ? videoFile.getOriginalFilename() : "NULL"));

        System.out.println("Video Empty: " +
                (videoFile == null || videoFile.isEmpty()));

        System.out.println("Thumbnail File: " +
                (thumbnailFile != null ? thumbnailFile.getOriginalFilename() : "NULL"));

        System.out.println("Thumbnail Empty: " +
                (thumbnailFile == null || thumbnailFile.isEmpty()));

        if (videoFile == null || videoFile.isEmpty()) {
            System.err.println("[ERROR] Video file is missing");
            throw new BadRequestException("Video file is required");
        }

        if (thumbnailFile == null || thumbnailFile.isEmpty()) {
            System.err.println("[ERROR] Thumbnail file is missing");
            throw new BadRequestException("Thumbnail file is required");
        }

        System.out.println("\n========== 2. GET CURRENT USER ==========");

        User user = authService.getCurrentUser();

        System.out.println("User ID: " + user.getId());
        System.out.println("User Email: " + user.getEmail());

        Video video = new Video();

        System.out.println("\n========== 3. CHECK DUPLICATE TITLE ==========");

        String title = request.getTitle().trim();

        System.out.println("Checking title: " + title);

        if (videoRepository.existsByUserAndTitleIgnoreCase(user, title)) {
            System.err.println("[ERROR] Video title already exists");
            throw new BadRequestException("Video already exists");
        }

        System.out.println("Duplicate check passed");

        video.setCategory(request.getCategory());
        video.setDescription(request.getDescription());
        video.setDisplayOrder(request.getDisplayOrder());
        video.setTitle(request.getTitle());
        video.setYear(request.getYear());
        video.setUser(user);

        System.out.println("\n========== 4. UPLOAD VIDEO ==========");

        try {
            CloudinaryVideoResponse upload =
                    cloudinaryService.uploadVideo(videoFile);

            System.out.println("Video upload success");
            System.out.println("Video URL: " + upload.getUrl());
            System.out.println("Video Duration: " + upload.getDuration());

            video.setVideoUrl(upload.getUrl());
            video.setDuration(formatDuration(upload.getDuration()));

            System.out.println("Formatted Duration: " + video.getDuration());

        } catch (Exception e) {
            System.err.println("[ERROR] UPLOAD VIDEO FAILED");
            e.printStackTrace();

            throw new RuntimeException("Upload Video Error", e);
        }

        System.out.println("\n========== 5. UPLOAD THUMBNAIL ==========");

        try {
            String thumbnailUrl =
                    cloudinaryService.uploadImage(thumbnailFile);

            System.out.println("Thumbnail upload success");
            System.out.println("Thumbnail URL: " + thumbnailUrl);

            video.setThumbnailUrl(thumbnailUrl);

        } catch (Exception e) {
            System.err.println("[ERROR] UPLOAD THUMBNAIL FAILED");
            e.printStackTrace();

            throw new RuntimeException("Upload Thumbnail Error", e);
        }

        System.out.println("\n========== 6. SAVE DATABASE ==========");

        Video saved = videoRepository.save(video);

        System.out.println("Database save success");
        System.out.println("Saved ID: " + saved.getId());
        System.out.println("Saved Title: " + saved.getTitle());
        System.out.println("Saved Video URL: " + saved.getVideoUrl());
        System.out.println("Saved Thumbnail URL: " + saved.getThumbnailUrl());

        System.out.println("\n========== 7. MAP RESPONSE ==========");

        VideoResponse response = videoMapper.toVideoResponse(saved);

        System.out.println("Response mapped successfully");
        System.out.println("Response: " + response);

        ApiResponse<VideoResponse> result = ApiResponse
                .<VideoResponse>builder()
                .status(200)
                .message("Create Video Successfully")
                .data(response)
                .build();

        System.out.println("\n========== 8. CREATE VIDEO SUCCESS ==========");
        System.out.println("Status: " + result.getStatus());
        System.out.println("Message: " + result.getMessage());
        System.out.println("============================================\n");

        return result;
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
        User user = authService.getCurrentUser();
        Video video = videoRepository.findByIdAndUser(id, user)
                .orElseThrow(() -> new RuntimeException("Video does not exist"));
        video.setTitle(request.getTitle());
        video.setDescription(request.getDescription());
        video.setYear(request.getYear());
        video.setDisplayOrder(request.getDisplayOrder());
        video.setCategory(request.getCategory());
        video.setUser(user);
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
        User user = authService.getCurrentUser();
        Video video = videoRepository.findByIdAndUser(id, user)
                .orElseThrow(() -> new BadRequestException("Video does not exist"));
        video.setUser(user);
        videoRepository.delete(video);
        return ApiResponse.<Void>builder()
                .status(200)
                .message("Delete Video Successfully")
                .build();
    }

    @Override
    public ApiResponse<List<VideoResponse>> getVideoByOnlyUser() {
        User user = authService.getCurrentUser();
        List<Video> videos = videoRepository.findAllByUserOrderByDisplayOrderAsc(user);
        List<VideoResponse> response = videos.stream()
                .map(videoMapper::toVideoResponse).toList();
        return ApiResponse.<List<VideoResponse>>builder()
                .status(200)
                .message("Get All Video By User Login Successfully")
                .data(response)
                .build();
    }

    @Override
    public ApiResponse<VideoResponse> getVideoByIdOnlyUser(UUID id) {
        User user = authService.getCurrentUser();
        Video videos = videoRepository.findByIdAndUser(id, user)
                .orElseThrow(() -> new BadRequestException("Video User By Id not found"));
        VideoResponse response = videoMapper.toVideoResponse(videos);
        return ApiResponse.<VideoResponse>builder()
                .status(200)
                .message("Get All Video By User Login Successfully")
                .data(response)
                .build();
    }
}