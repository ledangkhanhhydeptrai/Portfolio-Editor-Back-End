package com.example.demo.controller;

import com.example.demo.dto.request.CreateSkillRequest;
import com.example.demo.dto.request.UpdateSkillRequest;
import com.example.demo.dto.response.SkillResponse;
import com.example.demo.response.ApiResponse;
import com.example.demo.service.Interface.SkillService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api")
@Tag(name = "Skill")
public class SkillController {
    @Autowired
    private final SkillService skillService;

    public SkillController(SkillService skillService) {
        this.skillService = skillService;
    }

    @GetMapping("/public/skill")
    public ResponseEntity<ApiResponse<SkillResponse>> getAllSkill() {
        return ResponseEntity.ok(skillService.getAllSkill());
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/user/skill")
    public ResponseEntity<ApiResponse<List<SkillResponse>>> getAllSkillByUser() {
        return ResponseEntity.ok(skillService.getAllSkillByUser());
    }

    @GetMapping("/public/skill/{id}")
    public ResponseEntity<ApiResponse<SkillResponse>> getSkillById(@PathVariable UUID id) {
        return ResponseEntity.ok(skillService.getSkillById(id));
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/user/skill/{id}")
    public ResponseEntity<ApiResponse<SkillResponse>> getAllSkillByUserId(@PathVariable UUID id) {
        return ResponseEntity.ok(skillService.getSkillByUserId(id));
    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping(value = "/user/create-skill", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<SkillResponse>> createSkill(@Valid @ModelAttribute CreateSkillRequest request,
                                                                  @RequestPart(value = "iconUrl", required = false) MultipartFile iconUrl) {
        return ResponseEntity.ok(skillService.createSkill(request, iconUrl));
    }

    @PreAuthorize("hasRole('USER')")
    @PutMapping(value = "/user/update-skill/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<SkillResponse>> updateSkill(@PathVariable UUID id,
                                                                  @Valid @ModelAttribute UpdateSkillRequest request, @RequestPart(value = "iconUrl", required = false) MultipartFile iconUrl) {
        return ResponseEntity.ok(skillService.updateSkill(request, id, iconUrl));
    }

    @PreAuthorize("hasRole('USER')")
    @DeleteMapping("/user/delete-skill/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteSkill(@PathVariable UUID id) {
        return ResponseEntity.ok(skillService.deleteSkill(id));
    }
}
