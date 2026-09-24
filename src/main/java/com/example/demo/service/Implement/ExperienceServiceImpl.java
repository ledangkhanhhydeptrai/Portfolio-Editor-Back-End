package com.example.demo.service.Implement;

import com.example.demo.dto.response.ExperienceResponse;
import com.example.demo.entity.Experience;
import com.example.demo.mapper.ExperienceMapper;
import com.example.demo.repository.ExperienceRepository;
import com.example.demo.response.ApiResponse;
import com.example.demo.service.Interface.ExperienceService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ExperienceServiceImpl implements ExperienceService {
    private final ExperienceRepository experienceRepository;
    private final ExperienceMapper experienceMapper;

    public ExperienceServiceImpl(ExperienceRepository experienceRepository, ExperienceMapper experienceMapper) {
        this.experienceRepository = experienceRepository;
        this.experienceMapper = experienceMapper;
    }

    @Override
    public ApiResponse<List<ExperienceResponse>> getAllExperience() {
        List<Experience> experiences = experienceRepository.findAll();
        List<ExperienceResponse> responses = experiences.stream()
                .map(experienceMapper::toResponse)
                .toList();
        return ApiResponse.<List<ExperienceResponse>>builder()
                .status(200)
                .message("Get All Experience Successfully")
                .data(responses)
                .build();
    }
}
