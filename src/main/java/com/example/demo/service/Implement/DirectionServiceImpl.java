package com.example.demo.service.Implement;

import com.example.demo.dto.request.CreateDirectionRequest;
import com.example.demo.dto.response.DirectionResponse;
import com.example.demo.entity.Direction;
import com.example.demo.entity.Skill;
import com.example.demo.entity.User;
import com.example.demo.exception.BadRequestException;
import com.example.demo.mapper.DirectionMapper;
import com.example.demo.repository.DirectionRepository;
import com.example.demo.repository.SkillRepository;
import com.example.demo.response.ApiResponse;
import com.example.demo.service.Interface.AuthService;
import com.example.demo.service.Interface.DirectionService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class DirectionServiceImpl implements DirectionService {
    private final SkillRepository skillRepository;
    private final DirectionRepository directionRepository;

    private final DirectionMapper directionMapper;

    private final AuthService authService;
    @Value("${portfolio.owner.email}")
    private String portfolioOwnerEmail;

    public DirectionServiceImpl(
            DirectionRepository directionRepository,
            DirectionMapper directionMapper,
            AuthService authService,
            SkillRepository skillRepository
    ) {
        this.directionRepository = directionRepository;
        this.directionMapper = directionMapper;
        this.authService = authService;
        this.skillRepository = skillRepository;
    }

    @Override
    public ApiResponse<DirectionResponse> getAllDirection() {

        Direction directions =
                directionRepository.findFirstByUser_EmailOrderByDisplayOrderAsc(portfolioOwnerEmail)
                        .orElseThrow(() -> new BadRequestException("Public Direction Not Found"));

        DirectionResponse responses = directionMapper.toResponse(directions);

        return ApiResponse.<DirectionResponse>builder()
                .status(200)
                .message("Get All Direction Successfully")
                .data(responses)
                .build();
    }

    @Override
    public ApiResponse<List<DirectionResponse>> getAllDirectionByUser() {

        User user = authService.getCurrentUser();

        List<Direction> directions =
                directionRepository.findAllByUserOrderByDisplayOrderAsc(user);

        List<DirectionResponse> responses = directions.stream()
                .map(directionMapper::toResponse)
                .toList();

        return ApiResponse.<List<DirectionResponse>>builder()
                .status(200)
                .message("Get All Direction By User Successfully")
                .data(responses)
                .build();
    }

    @Override
    public ApiResponse<DirectionResponse> getDirectionById(UUID id) {

        Direction direction = directionRepository.findById(id)
                .orElseThrow(
                        () -> new BadRequestException(
                                "Direction Not Found"
                        )
                );

        DirectionResponse response =
                directionMapper.toResponse(direction);

        return ApiResponse.<DirectionResponse>builder()
                .status(200)
                .message("Get Direction Successfully")
                .data(response)
                .build();
    }

    @Override
    public ApiResponse<DirectionResponse> getDirectionByUserId(UUID id) {

        User user = authService.getCurrentUser();

        Direction direction = directionRepository
                .findByIdAndUser(id, user)
                .orElseThrow(
                        () -> new BadRequestException(
                                "Direction Not Found"
                        )
                );

        DirectionResponse response =
                directionMapper.toResponse(direction);

        return ApiResponse.<DirectionResponse>builder()
                .status(200)
                .message("Get Direction By User Successfully")
                .data(response)
                .build();
    }

    @Override
    public ApiResponse<DirectionResponse> createDirection(
            CreateDirectionRequest request
    ) {
        List<Skill> skills =
                skillRepository.findAllById(request.getSkills());

        User user = authService.getCurrentUser();

        Direction direction = new Direction();
        for (Skill skill : skills) {
            skill.setDirection(direction);
        }
        direction.setUser(user);
        direction.setCode(request.getCode());
        direction.setIcon(request.getIcon());
        direction.setTitle(request.getTitle());
        direction.setDescription(request.getDescription());
        direction.setDisplayOrder(request.getDisplayOrder());

        if (request.getSkills() != null) {
            direction.setSkills(
                    skills
            );
        } else {
            direction.setSkills(new ArrayList<>());
        }

        Direction savedDirection =
                directionRepository.save(direction);

        DirectionResponse response =
                directionMapper.toResponse(savedDirection);

        return ApiResponse.<DirectionResponse>builder()
                .status(200)
                .message("Create Direction Successfully")
                .data(response)
                .build();
    }

    @Override
    public ApiResponse<DirectionResponse> updateDirection(
            UUID id,
            CreateDirectionRequest request
    ) {
        List<Skill> skills =
                skillRepository.findAllById(request.getSkills());
        User user = authService.getCurrentUser();

        Direction direction = directionRepository
                .findByIdAndUser(id, user)
                .orElseThrow(
                        () -> new BadRequestException(
                                "Direction Not Found"
                        )
                );

        direction.setCode(request.getCode());
        direction.setIcon(request.getIcon());
        direction.setTitle(request.getTitle());
        direction.setDescription(request.getDescription());
        direction.setDisplayOrder(request.getDisplayOrder());

        if (request.getSkills() != null) {
            direction.setSkills(
                    skills
            );
        } else {
            direction.setSkills(new ArrayList<>());
        }

        Direction savedDirection =
                directionRepository.save(direction);

        DirectionResponse response =
                directionMapper.toResponse(savedDirection);

        return ApiResponse.<DirectionResponse>builder()
                .status(200)
                .message("Update Direction Successfully")
                .data(response)
                .build();
    }

    @Override
    public ApiResponse<Void> deleteDirection(UUID id) {

        User user = authService.getCurrentUser();

        Direction direction = directionRepository
                .findByIdAndUser(id, user)
                .orElseThrow(
                        () -> new BadRequestException(
                                "Direction Not Found"
                        )
                );

        directionRepository.delete(direction);

        return ApiResponse.<Void>builder()
                .status(200)
                .message("Delete Direction Successfully")
                .build();
    }
}