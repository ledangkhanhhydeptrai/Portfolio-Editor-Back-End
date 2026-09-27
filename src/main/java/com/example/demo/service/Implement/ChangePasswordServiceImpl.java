package com.example.demo.service.Implement;

import com.example.demo.dto.request.ChangePasswordRequest;
import com.example.demo.entity.User;
import com.example.demo.exception.BadRequestException;
import com.example.demo.repository.UserRepository;
import com.example.demo.response.ApiResponse;
import com.example.demo.service.Interface.ChangePasswordService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class ChangePasswordServiceImpl implements ChangePasswordService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public ChangePasswordServiceImpl(
            PasswordEncoder passwordEncoder,
            UserRepository userRepository
    ) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    @Override
    public ApiResponse<Void> changePassword(
            ChangePasswordRequest request
    ) {

        if (!request.getNewPassword().equals(
                request.getConfirmPassword()
        )) {
            throw new BadRequestException(
                    "Confirm password does not match"
            );
        }

        User user = userRepository
                .findByEmail(request.getEmail())
                .orElseThrow(
                        () -> new BadRequestException(
                                "Email not found"
                        )
                );

        user.setPassword(
                passwordEncoder.encode(
                        request.getNewPassword()
                )
        );

        userRepository.save(user);

        return ApiResponse
                .<Void>builder()
                .status(200)
                .message("Change Password Successfully")
                .build();
    }
}