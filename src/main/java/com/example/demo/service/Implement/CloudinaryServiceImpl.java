package com.example.demo.service.Implement;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.example.demo.service.Interface.CloudinaryService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Service
public class CloudinaryServiceImpl
        implements CloudinaryService {

    private final Cloudinary cloudinary;

    public CloudinaryServiceImpl(
            @Value("${cloudinary.cloud_name}")
            String cloudName,

            @Value("${cloudinary.api_key}")
            String apiKey,

            @Value("${cloudinary.api_secret}")
            String apiSecret
    ) {
        this.cloudinary = new Cloudinary(
                ObjectUtils.asMap(
                        "cloud_name", cloudName,
                        "api_key", apiKey,
                        "api_secret", apiSecret,
                        "secure", true
                )
        );
    }

    @Override
    public String uploadFile(
            MultipartFile file
    ) throws IOException {

        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException(
                    "File không được để trống"
            );
        }

        Map<?, ?> uploadResult =
                cloudinary
                        .uploader()
                        .upload(
                                file.getBytes(),
                                ObjectUtils.asMap(
                                        "folder",
                                        "portfolio",
                                        "resource_type",
                                        "auto"
                                )
                        );

        Object secureUrl =
                uploadResult.get(
                        "secure_url"
                );

        if (secureUrl == null) {
            throw new IOException(
                    "Cloudinary không trả về secure_url"
            );
        }

        return secureUrl.toString();
    }
}