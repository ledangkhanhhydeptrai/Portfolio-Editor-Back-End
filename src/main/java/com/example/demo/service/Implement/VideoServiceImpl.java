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
    public ApiResponse<VideoResponse>
    createVideo(
            VideoRequest request,
            MultipartFile videoFile,
            MultipartFile thumbnailFile

    ) {


        System.out.println(
                "======================================"
        );

        System.out.println(
                "DEBUG CREATE VIDEO - START"
        );

        System.out.println(
                "======================================"
        );

        /*
         * ==========================================
         * STEP 1 - REQUEST
         * ==========================================
         */

        System.out.println(
                "[STEP 1] Request received"
        );

        System.out.println(
                "Title: " +
                        request.getTitle()
        );

        System.out.println(
                "Category: " +
                        request.getCategory()
        );

        System.out.println(
                "Year: " +
                        request.getYear()
        );

        System.out.println(
                "Display order: " +
                        request.getDisplayOrder()
        );

        /*
         * ==========================================
         * STEP 2 - VIDEO FILE
         * ==========================================
         */

        System.out.println(
                "[STEP 2] Checking video file"
        );

        if (videoFile == null || videoFile.isEmpty()) {
            throw new BadRequestException("Video file is required");
        } else {

            System.out.println(
                    "Video name: " +
                            videoFile.getOriginalFilename()
            );

            System.out.println(
                    "Video content type: " +
                            videoFile.getContentType()
            );

            System.out.println(
                    "Video size: " +
                            videoFile.getSize() +
                            " bytes"
            );

            System.out.println(
                    "Video size MB: " +
                            (
                                    videoFile.getSize() /
                                            1024.0 /
                                            1024.0
                            )
            );

            System.out.println(
                    "Video empty: " +
                            videoFile.isEmpty()
            );
        }

        /*
         * ==========================================
         * STEP 3 - THUMBNAIL
         * ==========================================
         */

        System.out.println(
                "[STEP 3] Checking thumbnail"
        );

        if (thumbnailFile == null || thumbnailFile.isEmpty()) {

            throw new BadRequestException("Thumbnail file is required");

        } else {

            System.out.println(
                    "Thumbnail name: " +
                            thumbnailFile
                                    .getOriginalFilename()
            );

            System.out.println(
                    "Thumbnail size MB: " +
                            (
                                    thumbnailFile.getSize() /
                                            1024.0 /
                                            1024.0
                            )
            );
        }

        /*
         * ==========================================
         * STEP 4 - CURRENT USER
         * ==========================================
         */

        System.out.println(
                "[STEP 4] Getting current user..."
        );

        User user;

        try {

            user =
                    authService
                            .getCurrentUser();

            System.out.println(
                    "[STEP 4 SUCCESS]"
            );

            System.out.println(
                    "User ID: " +
                            user.getId()
            );

            System.out.println(
                    "User email: " +
                            user.getEmail()
            );

        } catch (Exception e) {

            System.err.println(
                    "[STEP 4 FAILED] GET CURRENT USER"
            );

            e.printStackTrace();

            throw e;
        }

        /*
         * ==========================================
         * STEP 5 - CREATE ENTITY
         * ==========================================
         */

        System.out.println(
                "[STEP 5] Creating Video entity..."
        );

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

        System.out.println(
                "[STEP 5 SUCCESS]"
        );

        /*
         * ==========================================
         * STEP 6 - CLOUDINARY VIDEO
         * ==========================================
         */

        try {

            if (
                    videoFile != null &&
                            !videoFile.isEmpty()
            ) {

                System.out.println(
                        "[STEP 6] Uploading VIDEO to Cloudinary..."
                );

                long startTime =
                        System.currentTimeMillis();

                CloudinaryVideoResponse upload =
                        cloudinaryService
                                .uploadVideo(
                                        videoFile
                                );

                long endTime =
                        System.currentTimeMillis();

                System.out.println(
                        "[STEP 6 SUCCESS]"
                );

                System.out.println(
                        "Upload time: " +
                                (endTime - startTime) +
                                " ms"
                );

                System.out.println(
                        "Cloudinary video URL: " +
                                upload.getUrl()
                );

                System.out.println(
                        "Cloudinary duration: " +
                                upload.getDuration()
                );

                video.setVideoUrl(
                        upload.getUrl()
                );

                video.setDuration(
                        formatDuration(
                                upload.getDuration()
                        )
                );

            } else {

                System.out.println(
                        "[STEP 6 SKIPPED] Video file empty"
                );
            }

        } catch (Exception e) {

            System.err.println(
                    "======================================"
            );

            System.err.println(
                    "[STEP 6 FAILED] VIDEO UPLOAD"
            );

            System.err.println(
                    "Exception: " +
                            e.getClass().getName()
            );

            System.err.println(
                    "Message: " +
                            e.getMessage()
            );

            e.printStackTrace();

            System.err.println(
                    "======================================"
            );

            throw new RuntimeException(
                    "Upload Video Error",
                    e
            );
        }

        /*
         * ==========================================
         * STEP 7 - CLOUDINARY THUMBNAIL
         * ==========================================
         */

        try {

            if (
                    thumbnailFile != null &&
                            !thumbnailFile.isEmpty()
            ) {

                System.out.println(
                        "[STEP 7] Uploading THUMBNAIL..."
                );

                String thumbnailUrl =
                        cloudinaryService
                                .uploadFile(
                                        thumbnailFile
                                );

                video.setThumbnailUrl(
                        thumbnailUrl
                );

                System.out.println(
                        "[STEP 7 SUCCESS]"
                );

                System.out.println(
                        "Thumbnail URL: " +
                                thumbnailUrl
                );

            } else {

                System.out.println(
                        "[STEP 7 SKIPPED] No thumbnail"
                );
            }

        } catch (Exception e) {

            System.err.println(
                    "[STEP 7 FAILED] THUMBNAIL UPLOAD"
            );

            System.err.println(
                    "Exception: " +
                            e.getClass().getName()
            );

            System.err.println(
                    "Message: " +
                            e.getMessage()
            );

            e.printStackTrace();

            throw new RuntimeException(
                    "Upload Thumbnail Error",
                    e
            );
        }

        /*
         * ==========================================
         * STEP 8 - DATABASE
         * ==========================================
         */

        System.out.println(
                "[STEP 8] Saving video to database..."
        );

        Video saved;

        try {

            saved =
                    videoRepository
                            .save(video);

            System.out.println(
                    "[STEP 8 SUCCESS]"
            );

            System.out.println(
                    "Video ID: " +
                            saved.getId()
            );

        } catch (Exception e) {

            System.err.println(
                    "[STEP 8 FAILED] DATABASE"
            );

            System.err.println(
                    "Exception: " +
                            e.getClass().getName()
            );

            System.err.println(
                    "Message: " +
                            e.getMessage()
            );

            e.printStackTrace();

            throw e;
        }

        /*
         * ==========================================
         * STEP 9 - RESPONSE
         * ==========================================
         */

        System.out.println(
                "[STEP 9] Mapping response..."
        );

        VideoResponse response =
                videoMapper
                        .toVideoResponse(
                                saved
                        );

        System.out.println(
                "======================================"
        );

        System.out.println(
                "DEBUG CREATE VIDEO - SUCCESS"
        );

        System.out.println(
                "======================================"
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
    public ApiResponse<Void> deleteVideoByUser(UUID id){
        Video video = videoRepository.findById(id)
                .orElseThrow(()-> new BadRequestException("Video does not exist"));
        videoRepository.delete(video);
        return ApiResponse.<Void> builder()
                .status(200)
                .message("Delete Video Successfully")
                .build();
    }
}