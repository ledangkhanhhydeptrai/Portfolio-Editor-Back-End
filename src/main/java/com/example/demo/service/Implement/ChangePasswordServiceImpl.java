package com.example.demo.service.Implement;

import com.example.demo.dto.request.ChangePasswordRequest;
import com.example.demo.dto.request.ForgotPasswordRequest;
import com.example.demo.dto.request.ResetPasswordRequest;
import com.example.demo.dto.request.VerifyOtpRequest;
import com.example.demo.dto.response.VerifyOtpResponse;
import com.example.demo.entity.PasswordResetOtp;
import com.example.demo.entity.User;
import com.example.demo.exception.BadRequestException;
import com.example.demo.mapper.VerifyOTPMapper;
import com.example.demo.repository.PasswordResetOTPRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.response.ApiResponse;
import com.example.demo.service.Interface.ChangePasswordService;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class ChangePasswordServiceImpl implements ChangePasswordService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final JavaMailSender javaMailSender;
    private final SecureRandom secureRandom;
    private final PasswordResetOTPRepository passwordResetOTPRepository;
    private final VerifyOTPMapper verifyOTPMapper;

    public ChangePasswordServiceImpl(
            PasswordEncoder passwordEncoder,
            UserRepository userRepository,
            JavaMailSender javaMailSender,
            SecureRandom secureRandom,
            PasswordResetOTPRepository passwordResetOTPRepository,
            VerifyOTPMapper verifyOTPMapper
    ) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.javaMailSender = javaMailSender;
        this.secureRandom = secureRandom;
        this.passwordResetOTPRepository = passwordResetOTPRepository;
        this.verifyOTPMapper = verifyOTPMapper;
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

    @Override
    public void sendPasswordResetOTP(String email, String otp) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Password Reset");
        message.setText("""
                Bạn vừa yêu cầu đặt lại mật khẩu.
                
                Mã OTP của bạn là:
                
                %s
                
                Mã OTP có hiệu lực trong 5 phút.
                
                Nếu bạn không yêu cầu đặt lại mật khẩu,
                vui lòng bỏ qua email này.
                """.formatted(otp));
        javaMailSender.send(message);
    }

    @Override
    public ApiResponse<Void> sendOtp(ForgotPasswordRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new BadRequestException("Email not found"));
        String otp = generateOtp();
        PasswordResetOtp resetOtp = passwordResetOTPRepository.findByUser(user).orElseGet(() -> PasswordResetOtp
                .builder()
                .user(user)
                .build());
        resetOtp.setOtp(otp);
        resetOtp.setExpiresAt(LocalDateTime.now().plusMinutes(5));
        resetOtp.setVerified(false);
        passwordResetOTPRepository.save(resetOtp);
        sendPasswordResetOTP(request.getEmail(), otp);
        return ApiResponse.<Void>builder()
                .status(200)
                .message("OTP has been sent to your email")
                .build();
    }

    private String generateOtp() {
        int number = secureRandom.nextInt(900000) + 100000;
        return String.format("%06d", number);
    }

    @Override
    public ApiResponse<VerifyOtpResponse> verifyOtp(VerifyOtpRequest request) {
        User user = userRepository.findByEmail(request.getEmail()).orElseThrow(() -> new BadRequestException("Email not found"));
        PasswordResetOtp resetOtp = passwordResetOTPRepository.findByUser(user).orElseThrow(() -> new BadRequestException("OTP not found"));
        if (LocalDateTime.now().isAfter(resetOtp.getExpiresAt())) {
            throw new BadRequestException("OTP has expired");
        }
        if (!resetOtp.getOtp().equals(request.getOtp())) {
            throw new BadRequestException(
                    "OTP is invalid"
            );
        }
        String resetToken = UUID.randomUUID().toString();
        resetOtp.setVerified(true);
        resetOtp.setResetToken(resetToken);
        resetOtp.setResetTokenExpiresAt(LocalDateTime.now().plusMinutes(10));
        PasswordResetOtp savedResetPasswordOTP = passwordResetOTPRepository.save(
                resetOtp
        );
        VerifyOtpResponse response = verifyOTPMapper.toVerifyOtpResponse(savedResetPasswordOTP);
        return ApiResponse
                .<VerifyOtpResponse>builder()
                .status(200)
                .message("OTP verified successfully")
                .data(response)
                .build();
    }

    @Override
    public ApiResponse<Void> resetPassword(ResetPasswordRequest resetPasswordRequest) {
        if (!resetPasswordRequest.getNewPassword().equals(resetPasswordRequest.getConfirmPassword())) {
            throw new BadRequestException(
                    "Confirm password does not match"
            );
        }
        PasswordResetOtp resetOtp = passwordResetOTPRepository.findByResetToken(resetPasswordRequest.getResetToken()).orElseThrow(
                () -> new BadRequestException(
                        "Reset token is invalid"
                )
        );
        if (!resetOtp.isVerified()) {
            throw new BadRequestException("Reset token is not verified");
        }
        if (resetOtp.getResetTokenExpiresAt() == null) {
            throw new BadRequestException(
                    "Reset token is invalid"
            );
        }
        if (LocalDateTime.now().isAfter(
                resetOtp.getResetTokenExpiresAt()
        )) {
            throw new BadRequestException(
                    "Reset token has expired"
            );
        }
        User user = resetOtp.getUser();
        user.setPassword(passwordEncoder.encode(resetPasswordRequest.getNewPassword()));
        userRepository.save(user);
        passwordResetOTPRepository.delete(resetOtp);
        return ApiResponse
                .<Void>builder()
                .status(200)
                .message("Password reset successfully")
                .build();
    }
}