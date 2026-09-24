package com.example.demo.controller;

import com.example.demo.dto.response.EducationResponse;
import com.example.demo.response.ApiResponse;
import com.example.demo.service.Interface.EducationService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/education")
@Tag(name = "Education")
public class EducationController {
    @Autowired
    private final EducationService educationService;

    public EducationController(EducationService educationService) {
        this.educationService = educationService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<EducationResponse>>> getAllEducation() {
        return ResponseEntity.ok(educationService.getAllEducation());
    }
}
