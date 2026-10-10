package com.example.demo.controller;

import com.example.demo.dto.request.CreateCVRequest;
import com.example.demo.dto.response.CVResponse;
import com.example.demo.response.ApiResponse;
import com.example.demo.service.Interface.CVService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api")
@Tag(name = "Upload CV")
public class CVController {
    @Autowired
    private final CVService cvService;

    public CVController(CVService cvService) {
        this.cvService = cvService;
    }

    @GetMapping("/public/CV")
    public ResponseEntity<ApiResponse<CVResponse>> getAllCV() {
        return ResponseEntity.ok(cvService.getAllCV());
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/user/CV")
    public ResponseEntity<ApiResponse<List<CVResponse>>> getAllCVByUser() {
        return ResponseEntity.ok(cvService.getAllCVByUser());
    }

    @GetMapping("/public/CV/{id}")
    public ResponseEntity<ApiResponse<CVResponse>> getCVById(@PathVariable UUID id) {
        return ResponseEntity.ok(cvService.getCVById(id));
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/user/CV/{id}")
    public ResponseEntity<ApiResponse<CVResponse>> getCVByUserId(@PathVariable UUID id) {
        return ResponseEntity.ok(cvService.getCVByUserId(id));
    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping(value = "/user/create-CV", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<CVResponse>> createCV(@Valid @ModelAttribute CreateCVRequest request) {
        return ResponseEntity.ok(cvService.createCV(request));
    }

    @PreAuthorize("hasRole('USER')")
    @PutMapping(value = "/user/create-cv/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<CVResponse>> updateCV(@PathVariable UUID id, @Valid @ModelAttribute CreateCVRequest request) {
        return ResponseEntity.ok(cvService.updateCV(id, request));
    }

    @PreAuthorize("hasRole('USER')")
    @DeleteMapping("/user/delete-CV/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteCV(@PathVariable UUID id) {
        return ResponseEntity.ok(cvService.deleteCV(id));
    }
}
