package com.example.demo.service.Interface;

import com.example.demo.dto.request.CreateSkillRequest;
import com.example.demo.dto.request.UpdateSkillRequest;
import com.example.demo.dto.response.SkillResponse;
import com.example.demo.response.ApiResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

public interface SkillService {
    ApiResponse<List<SkillResponse>> getAllSkill();

    ApiResponse<SkillResponse> getSkillById(UUID id);

    ApiResponse<SkillResponse> createSkill(CreateSkillRequest request, MultipartFile iconUrl);

    ApiResponse<SkillResponse> updateSkill(UpdateSkillRequest request, UUID id, MultipartFile iconUrl);

    ApiResponse<Void> deleteSkill(UUID id);

    ApiResponse<List<SkillResponse>> getAllSkillByUser();

    ApiResponse<SkillResponse> getSkillByUserId(UUID id);
}
