package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "password_reset_otp")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PasswordResetOtp {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(nullable = false, length = 6)
    private String otp;
    @Column(name = "expires_at", nullable = false)
    private LocalDateTime expiresAt;
    @Column(nullable = false)
    private boolean verified;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;
    @Column(name = "reset_token", unique = true)
    private String resetToken;
    @Column(name = "reset_token_expires_at")
    private LocalDateTime resetTokenExpiresAt;
}

