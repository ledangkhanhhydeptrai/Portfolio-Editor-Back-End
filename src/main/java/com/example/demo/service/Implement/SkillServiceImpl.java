package com.example.demo.service.Implement;

import com.example.demo.dto.request.CreateSkillRequest;
import com.example.demo.dto.request.UpdateSkillRequest;
import com.example.demo.dto.response.SkillResponse;
import com.example.demo.entity.Skill;
import com.example.demo.entity.User;
import com.example.demo.exception.BadRequestException;
import com.example.demo.mapper.SkillMapper;
import com.example.demo.repository.SkillRepository;
import com.example.demo.response.ApiResponse;
import com.example.demo.service.Interface.AuthService;
import com.example.demo.service.Interface.CloudinaryService;
import com.example.demo.service.Interface.SkillService;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@Service
public class SkillServiceImpl implements SkillService {
    private final SkillRepository skillRepository;
    private final SkillMapper skillMapper;
    private final CloudinaryService cloudinaryService;
    private final AuthService authService;

    public SkillServiceImpl(SkillRepository skillRepository, SkillMapper skillMapper, CloudinaryService cloudinaryService, AuthService authService) {
        this.skillRepository = skillRepository;
        this.skillMapper = skillMapper;
        this.cloudinaryService = cloudinaryService;
        this.authService = authService;
    }

    @Override
    public ApiResponse<List<SkillResponse>> getAllSkill() {
        List<Skill> skills = skillRepository.findAllByOrderByDisplayOrderAsc();
        List<SkillResponse> responses = skills.stream()
                .map(skillMapper::toSkillResponse)
                .toList();
        return ApiResponse.<List<SkillResponse>>builder()
                .status(200)
                .message("Get All Skill Successfully")
                .data(responses)
                .build();
    }

    @Override
    public ApiResponse<List<SkillResponse>> getAllSkillByUser() {
        User user = authService.getCurrentUser();
        System.out.println("CURRENT USER ID: " + user.getId());
        System.out.println("CURRENT USER EMAIL: " + user.getEmail());
        List<Skill> skills = skillRepository.findAllByUserOrderByDisplayOrderAsc(user);
        List<SkillResponse> responses = skills.stream()
                .map(skillMapper::toSkillResponse)
                .toList();
        return ApiResponse.<List<SkillResponse>>builder()
                .status(200)
                .message("Get All Skill By User Successfully")
                .data(responses)
                .build();
    }

    @Override
    public ApiResponse<SkillResponse> getSkillById(UUID id) {
        Skill skill = skillRepository.findById(id)
                .orElseThrow(() -> new BadRequestException("Skill Not Found"));
        SkillResponse skillResponse = skillMapper.toSkillResponse(skill);
        return ApiResponse.<SkillResponse>builder()
                .status(200)
                .message("Get Skill Successfully")
                .data(skillResponse)
                .build();
    }

    @Override
    public ApiResponse<SkillResponse> getSkillByUserId(UUID id) {
        User user = authService.getCurrentUser();
        Skill skill = skillRepository.findByIdAndUser(id, user)
                .orElseThrow(() -> new BadRequestException("Skill Not Found"));
        SkillResponse skillResponse = skillMapper.toSkillResponse(skill);
        return ApiResponse.<SkillResponse>builder()
                .status(200)
                .message("Get Skill Successfully")
                .data(skillResponse)
                .build();
    }

    @Override
    public ApiResponse<SkillResponse> createSkill(CreateSkillRequest request, MultipartFile iconUrl) {
        User user = authService.getCurrentUser();
        Skill skill = new Skill();
        if (skillRepository.existsByUserAndNameIgnoreCase(user, request.getName().trim())) {
            throw new BadRequestException("Skill Already Exists");
        }
        skill.setDisplayOrder(request.getDisplayOrder());
        skill.setName(request.getName());
        skill.setCategory(request.getCategory());
        skill.setUser(user);
        try {
            String image = cloudinaryService.uploadFile(iconUrl);
            skill.setIconUrl(image);
        } catch (Exception e) {
            throw new BadRequestException(e.getMessage());
        }
        Skill savedSkill = skillRepository.save(skill);
        SkillResponse skillResponse = skillMapper.toSkillResponse(savedSkill);
        return ApiResponse.<SkillResponse>builder()
                .status(200)
                .message("Create Skill Successfully")
                .data(skillResponse)
                .build();
    }

    @Override
    public ApiResponse<SkillResponse> updateSkill(UpdateSkillRequest request, UUID id, MultipartFile iconUrl) {
        Skill skill = skillRepository.findById(id)
                .orElseThrow(() -> new BadRequestException("Skill Not Found"));
        User user = authService.getCurrentUser();
        skill.setUser(user);
        skill.setDisplayOrder(request.getDisplayOrder());
        skill.setName(request.getName());
        skill.setCategory(request.getCategory());
        try {
            String image = cloudinaryService.uploadFile(iconUrl);
            skill.setIconUrl(image);
        } catch (Exception e) {
            throw new BadRequestException(e.getMessage());
        }
        Skill savedSkill = skillRepository.save(skill);
        SkillResponse skillResponse = skillMapper.toSkillResponse(savedSkill);
        return ApiResponse.<SkillResponse>builder()
                .status(200)
                .message("Update Skill Successfully")
                .data(skillResponse)
                .build();
    }

    @Override
    public ApiResponse<Void> deleteSkill(UUID id) {
        Skill skill = skillRepository.findById(id)
                .orElseThrow(() -> new BadRequestException("Skill Not Found"));
        skillRepository.delete(skill);
        return ApiResponse.<Void>builder()
                .status(200)
                .message("Delete Skill Successfully")
                .build();
    }
}
