package com.example.demo.service.Implement;

import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.Interface.AuthService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;

    public AuthServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (
                authentication == null ||
                        !authentication.isAuthenticated()
        ) {
            throw new RuntimeException(
                    "User chưa đăng nhập"
            );
        }
        String username = authentication.getName();
        return userRepository.findByEmail(username)
                .orElseThrow(() -> new RuntimeException("Username not found"));
    }
}
