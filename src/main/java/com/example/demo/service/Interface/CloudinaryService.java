package com.example.demo.service.Interface;

import com.example.demo.dto.response.CloudinaryVideoResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface CloudinaryService {

    String uploadFile(
            MultipartFile file
    ) throws IOException;

    CloudinaryVideoResponse uploadVideo(
            MultipartFile file
    ) throws IOException;
}