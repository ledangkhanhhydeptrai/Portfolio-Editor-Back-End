package com.example.demo.controller;

import com.example.demo.dto.request.CreateWorkStyleRequest;
import com.example.demo.dto.response.WorkStyleResponse;
import com.example.demo.response.ApiResponse;
import com.example.demo.service.Interface.WorkStyleService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api")
@Tag(name = "Work Style")
public class WorkStyleController {

    private final WorkStyleService workStyleService;

    public WorkStyleController(
            WorkStyleService workStyleService
    ) {
        this.workStyleService = workStyleService;
    }

    @GetMapping("/public/work-style")
    public ResponseEntity<ApiResponse<List<WorkStyleResponse>>>
    getAllWorkStyle() {

        return ResponseEntity.ok(
                workStyleService.getAllWorkStyle()
        );
    }

    @GetMapping("/public/work-style/{id}")
    public ResponseEntity<ApiResponse<WorkStyleResponse>>
    getWorkStyleById(
            @PathVariable UUID id
    ) {

        return ResponseEntity.ok(
                workStyleService.getWorkStyleById(id)
        );
    }

    @GetMapping("/user/work-style")
    public ResponseEntity<ApiResponse<List<WorkStyleResponse>>>
    getAllWorkStyleByUser() {

        return ResponseEntity.ok(
                workStyleService.getAllWorkStyleByUser()
        );
    }

    @GetMapping("/user/work-style/{id}")
    public ResponseEntity<ApiResponse<WorkStyleResponse>>
    getWorkStyleByUserId(
            @PathVariable UUID id
    ) {

        return ResponseEntity.ok(
                workStyleService.getWorkStyleByUserId(id)
        );
    }

    @PostMapping("/user/work-style")
    public ResponseEntity<ApiResponse<WorkStyleResponse>>
    createWorkStyle(
            @Valid @RequestBody CreateWorkStyleRequest request
    ) {

        return ResponseEntity.ok(
                workStyleService.createWorkStyle(request)
        );
    }

    @PutMapping("/user/work-style/{id}")
    public ResponseEntity<ApiResponse<WorkStyleResponse>>
    updateWorkStyle(
            @PathVariable UUID id,
            @Valid @RequestBody CreateWorkStyleRequest request
    ) {

        return ResponseEntity.ok(
                workStyleService.updateWorkStyle(id, request)
        );
    }

    @DeleteMapping("/user/work-style/{id}")
    public ResponseEntity<ApiResponse<Void>>
    deleteWorkStyle(
            @PathVariable UUID id
    ) {

        return ResponseEntity.ok(
                workStyleService.deleteWorkStyle(id)
        );
    }
}