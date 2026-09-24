package com.example.demo.service.Implement;

import com.example.demo.dto.response.SkillResponse;
import com.example.demo.entity.Skill;
import com.example.demo.mapper.SkillMapper;
import com.example.demo.repository.SkillRepository;
import com.example.demo.response.ApiResponse;
import com.example.demo.service.Interface.SkillService;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SkillServiceImpl implements SkillService {
    private final SkillRepository skillRepository;
    private final SkillMapper skillMapper;

    public SkillServiceImpl(SkillRepository skillRepository, SkillMapper skillMapper) {
        this.skillRepository = skillRepository;
        this.skillMapper = skillMapper;
    }

    @Override
    public ApiResponse<List<SkillResponse>> getAllSkill() {
        List<Skill> skills = skillRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));
        List<SkillResponse> responses = skills.stream()
                .map(skillMapper::toSkillResponse)
                .toList();
        return ApiResponse.<List<SkillResponse>>builder()
                .status(200)
                .message("Get All Skill Successfully")
                .data(responses)
                .build();
    }
}
