package com.example.demo.controller;

import com.example.demo.dto.response.ExperienceResponse;
import com.example.demo.response.ApiResponse;
import com.example.demo.service.Interface.ExperienceService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/experience")
@Tag(name = "Experience")
public class ExperienceController {
    @Autowired
    private final ExperienceService experienceService;

    public ExperienceController(ExperienceService experienceService) {
        this.experienceService = experienceService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<ExperienceResponse>>> getAllExperience() {
        return ResponseEntity.ok(experienceService.getAllExperience());
    }
}
