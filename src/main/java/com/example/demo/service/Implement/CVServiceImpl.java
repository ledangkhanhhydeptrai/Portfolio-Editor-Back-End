package com.example.demo.service.Implement;

import com.example.demo.dto.request.CreateCVRequest;
import com.example.demo.dto.response.CVResponse;
import com.example.demo.entity.CV;
import com.example.demo.entity.User;
import com.example.demo.exception.BadRequestException;
import com.example.demo.mapper.CVMapper;
import com.example.demo.repository.CVRepository;
import com.example.demo.response.ApiResponse;
import com.example.demo.service.Interface.AuthService;
import com.example.demo.service.Interface.CVService;
import com.example.demo.service.Interface.CloudinaryService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@Service
public class CVServiceImpl implements CVService {
    @Value("${portfolio.owner.email}")
    private String portfolioOwnerEmail;
    private final CVRepository cvRepository;
    private final CVMapper cvMapper;
    private final AuthService authService;
    private final CloudinaryService cloudinaryService;

    public CVServiceImpl(CVRepository cvRepository, AuthService authService, CloudinaryService cloudinaryService, CVMapper cvMapper) {
        this.cvRepository = cvRepository;
        this.authService = authService;
        this.cloudinaryService = cloudinaryService;
        this.cvMapper = cvMapper;
    }

    @Override
    public ApiResponse<CVResponse> getAllCV() {
        CV cv = cvRepository.findFirstByUser_EmailOrderByDisplayOrderAsc(portfolioOwnerEmail)
                .orElseThrow(()->new BadRequestException("CV Not Found"));
        CVResponse response = cvMapper.toCVResponse(cv);
        return ApiResponse.<CVResponse>builder()
                .status(200)
                .message("Get All CV Successfully")
                .data(response)
                .build();
    }

    @Override
    public ApiResponse<List<CVResponse>> getAllCVByUser() {
        User user = authService.getCurrentUser();
        List<CV> cv = cvRepository.findAllByUserOrderByDisplayOrderAsc(user);
        List<CVResponse> response = cv.stream().map(cvMapper::toCVResponse).toList();
        return ApiResponse.<List<CVResponse>>builder()
                .status(200)
                .message("Get All CV Successfully")
                .data(response)
                .build();
    }

    @Override
    public ApiResponse<CVResponse> getCVById(UUID id) {
        CV cv = cvRepository.findById(id).orElseThrow(() -> new BadRequestException("CV not found"));
        CVResponse response = cvMapper.toCVResponse(cv);
        return ApiResponse.<CVResponse>builder()
                .status(200)
                .message("Get CV Successfully")
                .data(response)
                .build();
    }

    @Override
    public ApiResponse<CVResponse> getCVByUserId(UUID id) {
        User user = authService.getCurrentUser();
        CV cv = cvRepository.findByIdAndUser(id, user)
                .orElseThrow(() -> new BadRequestException("CV not found"));
        CVResponse response = cvMapper.toCVResponse(cv);
        return ApiResponse.<CVResponse>builder()
                .status(200)
                .message("Get CV Successfully")
                .data(response)
                .build();
    }

    @Override
    public ApiResponse<CVResponse> createCV(
            CreateCVRequest request
    ) {

        User user =
                authService.getCurrentUser();

        MultipartFile fileUrl =
                request.getFileUrl();

        if (
                fileUrl == null ||
                        fileUrl.isEmpty()
        ) {
            throw new BadRequestException(
                    "CV file is required"
            );
        }

        if (
                !"application/pdf".equalsIgnoreCase(
                        fileUrl.getContentType()
                )
        ) {
            throw new BadRequestException(
                    "CV file must be PDF"
            );
        }

        CV cv = new CV();

        cv.setTitle(
                request.getTitle()
        );

        cv.setDisplayOrder(
                request.getDisplayOrder()
        );

        cv.setIsPrimary(
                request.getIsPrimary()
        );

        cv.setUser(user);

        try {

            String file =
                    cloudinaryService.uploadCV(
                            fileUrl
                    );

            cv.setFileUrl(file);

        } catch (Exception e) {

            throw new BadRequestException(
                    "Upload File Error: "
                            + e.getMessage()
            );
        }

        CV savedCV =
                cvRepository.save(cv);

        CVResponse response =
                cvMapper.toCVResponse(savedCV);

        return ApiResponse
                .<CVResponse>builder()
                .status(200)
                .message(
                        "Create CV Successfully"
                )
                .data(response)
                .build();
    }

    @Override
    public ApiResponse<CVResponse> updateCV(UUID id, CreateCVRequest request) {
        User user = authService.getCurrentUser();
        CV cv = cvRepository.findById(id)
                .orElseThrow(() -> new BadRequestException("CV not found"));
        cv.setTitle(request.getTitle());
        cv.setDisplayOrder(request.getDisplayOrder());
        cv.setIsPrimary(request.getIsPrimary());
        cv.setUser(user);
        try {
            String file = cloudinaryService.uploadCV(request.getFileUrl());
            cv.setFileUrl(file);
        } catch (Exception e) {
            throw new BadRequestException("Upload File Error: " + e.getMessage());
        }
        CV savedCV = cvRepository.save(cv);
        CVResponse response = cvMapper.toCVResponse(savedCV);
        return ApiResponse.<CVResponse>builder()
                .status(200)
                .message("Update CV Successfully")
                .data(response)
                .build();
    }

    @Override
    public ApiResponse<Void> deleteCV(UUID id) {
        User user = authService.getCurrentUser();
        CV cv = cvRepository.findByIdAndUser(id, user)
                .orElseThrow(() -> new BadRequestException("CV not found"));
        cvRepository.delete(cv);
        return ApiResponse.<Void>builder()
                .status(200)
                .message("Delete CV Successfully")
                .build();
    }
}
