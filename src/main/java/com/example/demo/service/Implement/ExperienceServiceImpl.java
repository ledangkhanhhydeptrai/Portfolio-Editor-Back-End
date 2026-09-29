package com.example.demo.service.Implement;

import com.example.demo.dto.request.CreateExperienceRequest;
import com.example.demo.dto.response.ExperienceResponse;
import com.example.demo.entity.Experience;
import com.example.demo.entity.User;
import com.example.demo.exception.BadRequestException;
import com.example.demo.mapper.ExperienceMapper;
import com.example.demo.repository.ExperienceRepository;
import com.example.demo.response.ApiResponse;
import com.example.demo.service.Interface.AuthService;
import com.example.demo.service.Interface.ExperienceService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ExperienceServiceImpl implements ExperienceService {
    private final ExperienceRepository experienceRepository;
    private final ExperienceMapper experienceMapper;
    private final AuthService authService;

    public ExperienceServiceImpl(ExperienceRepository experienceRepository, ExperienceMapper experienceMapper, AuthService authService) {
        this.experienceRepository = experienceRepository;
        this.experienceMapper = experienceMapper;
        this.authService = authService;
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

    @Override
    public ApiResponse<ExperienceResponse> getExperienceById(UUID id) {
        Experience experience = experienceRepository.findById(id).orElseThrow(() -> new BadRequestException("Experience Not Found"));
        ExperienceResponse responses = experienceMapper.toResponse(experience);
        return ApiResponse.<ExperienceResponse>builder()
                .status(200)
                .message("Get Experience Successfully")
                .data(responses)
                .build();
    }

    @Override
    public ApiResponse<ExperienceResponse> createExperience(CreateExperienceRequest request) {
        if (
                Boolean.FALSE.equals(request.getIsCurrent()) &&
                        request.getEndDate() == null
        ) {
            throw new BadRequestException(
                    "End date is required when experience is not current"
            );
        }

        if (
                Boolean.TRUE.equals(request.getIsCurrent()) &&
                        request.getEndDate() != null
        ) {
            throw new BadRequestException(
                    "End date must be empty for current experience"
            );
        }

        if (
                request.getEndDate() != null &&
                        !request.getEndDate().isAfter(
                                request.getStartDate()
                        )
        ) {
            throw new BadRequestException(
                    "End date must be after start date"
            );
        }

        User user = authService.getCurrentUser();
        Experience experience = new Experience();
        experience.setUser(user);
        experience.setPosition(request.getPosition());
        experience.setCompanyName(request.getCompanyName());
        experience.setDescription(request.getDescription());
        experience.setDisplayOrder(request.getDisplayOrder());
        experience.setStartDate(request.getStartDate());
        experience.setEndDate(request.getEndDate());
        experience.setIsCurrent(request.getIsCurrent());
        Experience savedExperience = experienceRepository.save(experience);
        ExperienceResponse responses = experienceMapper.toResponse(savedExperience);
        return ApiResponse.<ExperienceResponse>builder()
                .status(200)
                .message("Create Experience Successfully")
                .data(responses)
                .build();
    }

    @Override
    public ApiResponse<ExperienceResponse> updateExperience(UUID id, CreateExperienceRequest request) {
        Experience experience = experienceRepository.findById(id).orElseThrow(() -> new BadRequestException("Experience Not Found"));
        experience.setPosition(request.getPosition());
        experience.setCompanyName(request.getCompanyName());
        experience.setDescription(request.getDescription());
        experience.setDisplayOrder(request.getDisplayOrder());
        experience.setStartDate(request.getStartDate());
        experience.setEndDate(request.getEndDate());
        experience.setIsCurrent(request.getIsCurrent());
        Experience savedExperience = experienceRepository.save(experience);
        ExperienceResponse responses = experienceMapper.toResponse(savedExperience);
        return ApiResponse.<ExperienceResponse>builder()
                .status(200)
                .message("Update Experience Successfully")
                .data(responses)
                .build();
    }

    @Override
    public ApiResponse<Void> deleteExperience(UUID id) {
        Experience experience = experienceRepository.findById(id).orElseThrow(() -> new BadRequestException("Experience Not Found"));
        experienceRepository.delete(experience);
        return ApiResponse.<Void>builder()
                .status(200)
                .message("Delete Experience Successfully")
                .build();
    }
}
