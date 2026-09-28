package com.example.demo.controller;

import com.example.demo.dto.request.UpdateVideoRequest;
import com.example.demo.dto.request.VideoCreateSwaggerRequest;
import com.example.demo.dto.request.VideoRequest;
import com.example.demo.dto.response.VideoResponse;
import com.example.demo.response.ApiResponse;
import com.example.demo.service.Interface.VideoService;

import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api")
@Tag(name = "Video")
public class VideoController {

    private final VideoService videoService;

    public VideoController(
            VideoService videoService
    ) {
        this.videoService = videoService;
    }

    @GetMapping("/public/video")
    public ResponseEntity<
            ApiResponse<List<VideoResponse>>
            > getAllVideos() {

        return ResponseEntity.ok(
                videoService.getAllVideo()
        );
    }

    @Operation(
            requestBody = @RequestBody(
                    required = true,
                    content = @Content(
                            mediaType = MediaType.MULTIPART_FORM_DATA_VALUE,
                            schema = @Schema(
                                    implementation =
                                            VideoCreateSwaggerRequest.class
                            )
                    )
            )
    )
    @PreAuthorize("hasRole('USER')")
    @PostMapping(
            value = "/video",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public ResponseEntity<ApiResponse<VideoResponse>> createVideo(

            @Valid
            @ModelAttribute
            VideoRequest request,

            @RequestPart(
                    value = "videoFile",
                    required = false
            )
            MultipartFile videoFile,

            @RequestPart(
                    value = "thumbnailFile",
                    required = false
            )
            MultipartFile thumbnailFile
    ) {

        return ResponseEntity.ok(
                videoService.createVideo(
                        request,
                        videoFile,
                        thumbnailFile
                )
        );
    }

    @GetMapping("/public/video/{id}")
    public ResponseEntity<ApiResponse<VideoResponse>> getVideoById(@PathVariable UUID id) {
        return ResponseEntity.ok(
                videoService.getVideoById(id));
    }
    @PreAuthorize("hasRole('USER')")
    @PutMapping(value = "/video/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<VideoResponse>> updateVideo(@PathVariable UUID id, @Valid @ModelAttribute UpdateVideoRequest request) {
        return ResponseEntity.ok(videoService.updateVideoByUser(id, request));
    }
    @PreAuthorize("hasRole('USER')")
    @DeleteMapping("/video/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteVideoById(@PathVariable UUID id) {
        return ResponseEntity.ok(videoService.deleteVideoByUser(id));
    }
}