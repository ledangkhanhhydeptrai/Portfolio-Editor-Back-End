package com.example.demo.service.Implement;

import com.example.demo.dto.request.CreateEducationRequest;
import com.example.demo.dto.response.EducationResponse;
import com.example.demo.entity.Education;
import com.example.demo.entity.User;
import com.example.demo.exception.BadRequestException;
import com.example.demo.mapper.EducationMapper;
import com.example.demo.repository.EducationRepository;
import com.example.demo.response.ApiResponse;
import com.example.demo.service.Interface.AuthService;
import com.example.demo.service.Interface.EducationService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class EducationServiceImpl implements EducationService {
    private final EducationRepository educationRepository;
    private final EducationMapper educationMapper;
    private final AuthService authService;

    public EducationServiceImpl(EducationRepository educationRepository, EducationMapper educationMapper, AuthService authService) {
        this.educationRepository = educationRepository;
        this.educationMapper = educationMapper;
        this.authService = authService;
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

    @Override
    public ApiResponse<EducationResponse> getEducationById(UUID id) {
        Education education = educationRepository.findById(id).orElseThrow(() -> new BadRequestException("Education Not Found"));
        EducationResponse responses = educationMapper.toEducationResponse(education);
        return ApiResponse.<EducationResponse>builder()
                .status(200)
                .message("Get Education Successfully")
                .data(responses)
                .build();
    }

    @Override
    public ApiResponse<EducationResponse> createEducation(CreateEducationRequest request) {
        User user = authService.getCurrentUser();
        Education education = new Education();
        education.setUser(user);
        education.setDegree(request.getDegree());
        education.setDescription(request.getDescription());
        education.setDisplayOrder(request.getDisplayOrder());
        education.setEndYear(request.getEndYear());
        education.setStartYear(request.getStartYear());
        education.setSchoolName(request.getSchoolName());
        education.setMajor(request.getMajor());
        Education saved = educationRepository.save(education);
        EducationResponse responses = educationMapper.toEducationResponse(saved);
        return ApiResponse.<EducationResponse>builder()
                .status(200)
                .message("Create Education Successfully")
                .data(responses)
                .build();
    }

    @Override
    public ApiResponse<EducationResponse> updateEducation(UUID id, CreateEducationRequest request) {
        Education education = educationRepository.findById(id).orElseThrow(() -> new BadRequestException("Education Not Found"));
        education.setDegree(request.getDegree());
        education.setDescription(request.getDescription());
        education.setDisplayOrder(request.getDisplayOrder());
        education.setEndYear(request.getEndYear());
        education.setStartYear(request.getStartYear());
        education.setSchoolName(request.getSchoolName());
        education.setMajor(request.getMajor());
        Education saved = educationRepository.save(education);
        EducationResponse responses = educationMapper.toEducationResponse(saved);
        return ApiResponse.<EducationResponse>builder()
                .status(200)
                .message("Update Education Successfully")
                .data(responses)
                .build();
    }

    @Override
    public ApiResponse<Void> deleteEducation(UUID id) {
        Education education = educationRepository.findById(id).orElseThrow(() -> new BadRequestException("Education Not Found"));
        educationRepository.delete(education);
        return ApiResponse.<Void>builder()
                .status(200)
                .message("Delete Education Successfully")
                .build();
    }
}
