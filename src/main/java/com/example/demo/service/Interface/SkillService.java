package com.example.demo.service.Interface;

import com.example.demo.dto.response.SkillResponse;
import com.example.demo.response.ApiResponse;

import java.util.List;

public interface SkillService {
    ApiResponse<List<SkillResponse>> getAllSkill();
}
