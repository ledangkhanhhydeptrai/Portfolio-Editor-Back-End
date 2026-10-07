package com.example.demo.controller;

import com.example.demo.dto.request.CreateDirectionRequest;
import com.example.demo.dto.response.DirectionResponse;
import com.example.demo.response.ApiResponse;
import com.example.demo.service.Interface.DirectionService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api")
@Tag(name = "Direction")
public class DirectionController {

    private final DirectionService directionService;

    public DirectionController(
            DirectionService directionService
    ) {
        this.directionService = directionService;
    }

    @GetMapping("/public/direction")
    public ResponseEntity<ApiResponse<DirectionResponse>>
    getAllDirection() {

        return ResponseEntity.ok(
                directionService.getAllDirection()
        );
    }

    @GetMapping("/public/direction/{id}")
    public ResponseEntity<ApiResponse<DirectionResponse>>
    getDirectionById(
            @PathVariable UUID id
    ) {

        return ResponseEntity.ok(
                directionService.getDirectionById(id)
        );
    }

    @GetMapping("/user/direction")
    public ResponseEntity<ApiResponse<List<DirectionResponse>>>
    getAllDirectionByUser() {

        return ResponseEntity.ok(
                directionService.getAllDirectionByUser()
        );
    }

    @GetMapping("/user/direction/{id}")
    public ResponseEntity<ApiResponse<DirectionResponse>>
    getDirectionByUserId(
            @PathVariable UUID id
    ) {

        return ResponseEntity.ok(
                directionService.getDirectionByUserId(id)
        );
    }

    @PostMapping("/user/direction")
    public ResponseEntity<ApiResponse<DirectionResponse>>
    createDirection(
            @Valid @RequestBody CreateDirectionRequest request
    ) {

        return ResponseEntity.ok(
                directionService.createDirection(request)
        );
    }

    @PutMapping("/user/direction/{id}")
    public ResponseEntity<ApiResponse<DirectionResponse>>
    updateDirection(
            @PathVariable UUID id,
            @Valid @RequestBody CreateDirectionRequest request
    ) {

        return ResponseEntity.ok(
                directionService.updateDirection(id, request)
        );
    }

    @DeleteMapping("/user/direction/{id}")
    public ResponseEntity<ApiResponse<Void>>
    deleteDirection(
            @PathVariable UUID id
    ) {

        return ResponseEntity.ok(
                directionService.deleteDirection(id)
        );
    }
}