package com.example.demo.service.Implement;

import com.example.demo.dto.response.EducationResponse;
import com.example.demo.entity.Education;
import com.example.demo.mapper.EducationMapper;
import com.example.demo.repository.EducationRepository;
import com.example.demo.response.ApiResponse;
import com.example.demo.service.Interface.EducationService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EducationServiceImpl implements EducationService {
    private final EducationRepository educationRepository;
    private final EducationMapper educationMapper;

    public EducationServiceImpl(EducationRepository educationRepository, EducationMapper educationMapper) {
        this.educationRepository = educationRepository;
        this.educationMapper = educationMapper;
    }

    @Override
    public ApiResponse<List<EducationResponse>> getAllEducation() {
        List<Education> educations = educationRepository.findAll();
        List<EducationResponse> responses = educations.stream()
                .map(educationMapper::toEducationResponse)
                .toList();
        return ApiResponse.<List<EducationResponse>>builder()
                .status(200)
                .message("Get All Education Successfully")
                .data(responses)
                .build();
    }
}
