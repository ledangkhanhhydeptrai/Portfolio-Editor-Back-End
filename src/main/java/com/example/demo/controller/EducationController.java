package com.example.demo.controller;

import com.example.demo.dto.request.CreateEducationRequest;
import com.example.demo.dto.response.EducationResponse;
import com.example.demo.response.ApiResponse;
import com.example.demo.service.Interface.EducationService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api")
@Tag(name = "Education")
public class EducationController {
    @Autowired
    private final EducationService educationService;

    public EducationController(EducationService educationService) {
        this.educationService = educationService;
    }

    @GetMapping("/public/education")
    public ResponseEntity<ApiResponse<EducationResponse>> getAllEducation() {
        return ResponseEntity.ok(educationService.getAllEducation());
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/user/education")
    public ResponseEntity<ApiResponse<List<EducationResponse>>> getAllEducationByUser() {
        return ResponseEntity.ok(educationService.getAllEducationByUser());
    }

    @GetMapping("/public/education/{id}")
    public ResponseEntity<ApiResponse<EducationResponse>> getEducationById(@PathVariable UUID id) {
        return ResponseEntity.ok(educationService.getEducationById(id));
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/user/education/{id}")
    public ResponseEntity<ApiResponse<EducationResponse>> getAllEducationByUserId(UUID id) {
        return ResponseEntity.ok(educationService.getAllEducationByUserId(id));
    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping("/create-education")
    public ResponseEntity<ApiResponse<EducationResponse>> createEducation(@RequestBody CreateEducationRequest request) {
        return ResponseEntity.ok(educationService.createEducation(request));
    }

    @PreAuthorize("hasRole('USER')")
    @PutMapping("/update-education/{id}")
    public ResponseEntity<ApiResponse<EducationResponse>> updateEducation(@PathVariable UUID id, @RequestBody CreateEducationRequest request) {
        return ResponseEntity.ok(educationService.updateEducation(id, request));
    }

    @PreAuthorize("hasRole('USER')")
    @DeleteMapping("/delete-education/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteEducation(@PathVariable UUID id) {
        return ResponseEntity.ok(educationService.deleteEducation(id));
    }
}
