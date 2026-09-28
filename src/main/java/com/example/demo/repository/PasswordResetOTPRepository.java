package com.example.demo.repository;

import com.example.demo.entity.PasswordResetOtp;
import com.example.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PasswordResetOTPRepository extends JpaRepository<PasswordResetOtp, UUID> {
    Optional<PasswordResetOtp> findByUser(User user);

    Optional<PasswordResetOtp> findByResetToken(
            String resetToken
    );
}
