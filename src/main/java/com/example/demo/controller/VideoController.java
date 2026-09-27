package com.example.demo.controller;

import com.example.demo.dto.request.VideoRequest;
import com.example.demo.dto.response.VideoResponse;
import com.example.demo.response.ApiResponse;
import com.example.demo.service.Interface.VideoService;

import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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

    @PostMapping(
            value = "/video",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public ResponseEntity<
            ApiResponse<VideoResponse>
            > createVideo(
            @ModelAttribute VideoRequest request,

            @RequestPart("videoFile")
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
    public ResponseEntity<ApiResponse<VideoResponse>> getVideoById(@RequestParam UUID id) {
        return ResponseEntity.ok(
                videoService.getVideoById(id));
    }
}