package com.example.demo.controller;

import com.example.demo.dto.response.SkillResponse;
import com.example.demo.response.ApiResponse;
import com.example.demo.service.Interface.SkillService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/skill")
@Tag(name = "Skill")
public class SkillController {
    @Autowired
    private final SkillService skillService;

    public SkillController(SkillService skillService) {
        this.skillService = skillService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<SkillResponse>>> getAllSkill() {
        return ResponseEntity.ok(skillService.getAllSkill());
    }
}
