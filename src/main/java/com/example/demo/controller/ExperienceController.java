package com.example.demo.controller;

import com.example.demo.dto.request.CreateExperienceRequest;
import com.example.demo.dto.response.ExperienceResponse;
import com.example.demo.response.ApiResponse;
import com.example.demo.service.Interface.ExperienceService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api")
@Tag(name = "Experience")
public class ExperienceController {
    @Autowired
    private final ExperienceService experienceService;

    public ExperienceController(ExperienceService experienceService) {
        this.experienceService = experienceService;
    }

    @GetMapping("/public/experience")
    public ResponseEntity<ApiResponse<List<ExperienceResponse>>> getAllExperience() {
        return ResponseEntity.ok(experienceService.getAllExperience());
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/user/experience")
    public ResponseEntity<ApiResponse<List<ExperienceResponse>>> getAllExperienceByUser() {
        return ResponseEntity.ok(experienceService.getAllExperienceByUser());
    }

    @GetMapping("/public/experience/{id}")
    public ResponseEntity<ApiResponse<ExperienceResponse>> getExperienceById(@PathVariable UUID id) {
        return ResponseEntity.ok(experienceService.getExperienceById(id));
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/user/experience/{id}")
    public ResponseEntity<ApiResponse<ExperienceResponse>> getAllExperienceByUserId(UUID id) {
        return ResponseEntity.ok(experienceService.getAllExperienceByUserId(id));
    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping("/create-experience")
    public ResponseEntity<ApiResponse<ExperienceResponse>> createExperience(@Valid @RequestBody CreateExperienceRequest request) {
        return ResponseEntity.ok(experienceService.createExperience(request));
    }

    @PreAuthorize("hasRole('USER')")
    @PutMapping("/update-experience/{id}")
    public ResponseEntity<ApiResponse<ExperienceResponse>> updateExperience(@PathVariable UUID id, @Valid @RequestBody CreateExperienceRequest request) {
        return ResponseEntity.ok(experienceService.updateExperience(id, request));
    }

    @PreAuthorize("hasRole('USER')")
    @DeleteMapping("/delete-experience/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteExperience(@PathVariable UUID id) {
        return ResponseEntity.ok(experienceService.deleteExperience(id));
    }
}
