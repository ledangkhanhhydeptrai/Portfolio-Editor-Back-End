package com.example.demo.controller;

import com.example.demo.dto.request.*;
import com.example.demo.dto.response.LoginResponse;
import com.example.demo.dto.response.VerifyOtpResponse;
import com.example.demo.response.ApiResponse;
import com.example.demo.service.Interface.ChangePasswordService;
import com.example.demo.service.Interface.LoginService;
import com.example.demo.service.Interface.RegisterService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "Authentication")
public class AuthController {
    private final RegisterService registerService;
    private final LoginService loginService;
    private final ChangePasswordService changePasswordService;

    public AuthController(RegisterService registerService, LoginService loginService, ChangePasswordService changePasswordService) {
        this.registerService = registerService;
        this.loginService = loginService;
        this.changePasswordService = changePasswordService;
    }

    @PostMapping(
            value = "/register",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public ResponseEntity<ApiResponse<Void>> register(
            @Valid @ModelAttribute RegisterRequest request
    ) {

        registerService.registerUser(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.<Void>builder()
                        .status(201)
                        .message("Tạo tài khoản thành công")
                        .build());
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponse>> login(
            @RequestBody LoginRequest request
    ) {
        LoginResponse loginResponse =
                loginService.login(request);

        ResponseCookie accessTokenCookie =
                ResponseCookie.from(
                                "access_token",
                                loginResponse.getToken()
                        )
                        .httpOnly(true)
                        .secure(false)
                        .sameSite("Lax")
                        .path("/")
                        .maxAge(Duration.ofHours(1))
                        .build();

        return ResponseEntity
                .ok()
                .header(
                        HttpHeaders.SET_COOKIE,
                        accessTokenCookie.toString()
                )
                .body(
                        ApiResponse
                                .<LoginResponse>builder()
                                .status(200)
                                .message("Đăng nhập thành công")
                                .data(loginResponse)
                                .build()
                );
    }

    @PostMapping("/logout")
    public ResponseEntity<ApiResponse<Void>> logout() {
        ResponseCookie deleteCookie =
                ResponseCookie.from(
                                "access_token",
                                ""
                        )
                        .httpOnly(true)
                        .secure(false)
                        .sameSite("Lax")
                        .path("/")
                        .maxAge(Duration.ZERO)
                        .build();

        return ResponseEntity
                .ok()
                .header(
                        HttpHeaders.SET_COOKIE,
                        deleteCookie.toString()
                )
                .body(
                        ApiResponse
                                .<Void>builder()
                                .status(200)
                                .message("Đăng xuất thành công")
                                .data(null)
                                .build()
                );
    }

    @PutMapping("/change-password")
    public ResponseEntity<ApiResponse<Void>> changePassword(
            @Valid
            @RequestBody
            ChangePasswordRequest request
    ) {
        return ResponseEntity.ok(
                changePasswordService.changePassword(request)
        );
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<ApiResponse<Void>> forgotPassword(@Valid @RequestBody ForgotPasswordRequest request) {
        return ResponseEntity.ok(changePasswordService.sendOtp(request));
    }

    @PostMapping("/verify-otp")
    public ResponseEntity<ApiResponse<VerifyOtpResponse>> verifyOtp(
            @Valid @RequestBody VerifyOtpRequest request
    ) {
        return ResponseEntity.ok(
                changePasswordService.verifyOtp(request)
        );
    }

    @PutMapping("/reset-password")
    public ResponseEntity<ApiResponse<Void>> resetPassword(
            @Valid @RequestBody ResetPasswordRequest request
    ) {
        return ResponseEntity.ok(
                changePasswordService.resetPassword(request)
        );
    }
}
