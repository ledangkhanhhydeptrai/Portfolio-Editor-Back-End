package com.example.demo.service.Implement;

import com.example.demo.dto.request.CreateWorkStyleRequest;
import com.example.demo.dto.response.WorkStyleResponse;
import com.example.demo.entity.User;
import com.example.demo.entity.WorkStyle;
import com.example.demo.exception.BadRequestException;
import com.example.demo.mapper.WorkStyleMapper;
import com.example.demo.repository.WorkStyleRepository;
import com.example.demo.response.ApiResponse;
import com.example.demo.service.Interface.AuthService;
import com.example.demo.service.Interface.WorkStyleService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class WorkStyleServiceImpl
        implements WorkStyleService {

    private final WorkStyleRepository workStyleRepository;

    private final WorkStyleMapper workStyleMapper;

    private final AuthService authService;
    @Value("${portfolio.owner.email}")
    private String portfolioOwnerEmail;

    public WorkStyleServiceImpl(
            WorkStyleRepository workStyleRepository,
            WorkStyleMapper workStyleMapper,
            AuthService authService
    ) {
        this.workStyleRepository = workStyleRepository;
        this.workStyleMapper = workStyleMapper;
        this.authService = authService;
    }

    @Override
    public ApiResponse<WorkStyleResponse>
    getAllWorkStyle() {

        WorkStyle workStyles =
                workStyleRepository
                        .findFirstByUser_EmailOrderByDisplayOrderAsc(portfolioOwnerEmail)
                        .orElseThrow(() -> new BadRequestException("Public Work Style Not Found"));;

        WorkStyleResponse responses = workStyleMapper.toResponse(workStyles);

        return ApiResponse
                .<WorkStyleResponse>builder()
                .status(200)
                .message("Get All Work Style Successfully")
                .data(responses)
                .build();
    }

    @Override
    public ApiResponse<List<WorkStyleResponse>>
    getAllWorkStyleByUser() {

        User user = authService.getCurrentUser();

        List<WorkStyle> workStyles =
                workStyleRepository
                        .findAllByUserOrderByDisplayOrderAsc(user);

        List<WorkStyleResponse> responses =
                workStyles.stream()
                        .map(workStyleMapper::toResponse)
                        .toList();

        return ApiResponse
                .<List<WorkStyleResponse>>builder()
                .status(200)
                .message(
                        "Get All Work Style By User Successfully"
                )
                .data(responses)
                .build();
    }

    @Override
    public ApiResponse<WorkStyleResponse>
    getWorkStyleById(UUID id) {

        WorkStyle workStyle =
                workStyleRepository.findById(id)
                        .orElseThrow(
                                () -> new BadRequestException(
                                        "Work Style Not Found"
                                )
                        );

        WorkStyleResponse response =
                workStyleMapper.toResponse(workStyle);

        return ApiResponse
                .<WorkStyleResponse>builder()
                .status(200)
                .message("Get Work Style Successfully")
                .data(response)
                .build();
    }

    @Override
    public ApiResponse<WorkStyleResponse>
    getWorkStyleByUserId(UUID id) {

        User user = authService.getCurrentUser();

        WorkStyle workStyle =
                workStyleRepository
                        .findByIdAndUser(id, user)
                        .orElseThrow(
                                () -> new BadRequestException(
                                        "Work Style Not Found"
                                )
                        );

        WorkStyleResponse response =
                workStyleMapper.toResponse(workStyle);

        return ApiResponse
                .<WorkStyleResponse>builder()
                .status(200)
                .message(
                        "Get Work Style By User Successfully"
                )
                .data(response)
                .build();
    }

    @Override
    public ApiResponse<WorkStyleResponse>
    createWorkStyle(
            CreateWorkStyleRequest request
    ) {

        User user = authService.getCurrentUser();

        WorkStyle workStyle = new WorkStyle();

        workStyle.setUser(user);
        workStyle.setTitle(request.getTitle());
        workStyle.setDescription(
                request.getDescription()
        );
        workStyle.setDisplayOrder(
                request.getDisplayOrder()
        );

        WorkStyle savedWorkStyle =
                workStyleRepository.save(workStyle);

        WorkStyleResponse response =
                workStyleMapper.toResponse(savedWorkStyle);

        return ApiResponse
                .<WorkStyleResponse>builder()
                .status(200)
                .message("Create Work Style Successfully")
                .data(response)
                .build();
    }

    @Override
    public ApiResponse<WorkStyleResponse>
    updateWorkStyle(
            UUID id,
            CreateWorkStyleRequest request
    ) {

        User user = authService.getCurrentUser();

        WorkStyle workStyle =
                workStyleRepository
                        .findByIdAndUser(id, user)
                        .orElseThrow(
                                () -> new BadRequestException(
                                        "Work Style Not Found"
                                )
                        );

        workStyle.setTitle(request.getTitle());
        workStyle.setDescription(
                request.getDescription()
        );
        workStyle.setDisplayOrder(
                request.getDisplayOrder()
        );

        WorkStyle savedWorkStyle =
                workStyleRepository.save(workStyle);

        WorkStyleResponse response =
                workStyleMapper.toResponse(savedWorkStyle);

        return ApiResponse
                .<WorkStyleResponse>builder()
                .status(200)
                .message("Update Work Style Successfully")
                .data(response)
                .build();
    }

    @Override
    public ApiResponse<Void>
    deleteWorkStyle(UUID id) {

        User user = authService.getCurrentUser();

        WorkStyle workStyle =
                workStyleRepository
                        .findByIdAndUser(id, user)
                        .orElseThrow(
                                () -> new BadRequestException(
                                        "Work Style Not Found"
                                )
                        );

        workStyleRepository.delete(workStyle);

        return ApiResponse.<Void>builder()
                .status(200)
                .message("Delete Work Style Successfully")
                .build();
    }
}