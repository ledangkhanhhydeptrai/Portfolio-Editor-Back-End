package com.example.demo.service.Implement;

import com.example.demo.dto.request.LoginRequest;
import com.example.demo.dto.response.LoginServiceResult;
import com.example.demo.entity.User;
import com.example.demo.exception.BadRequestException;
import com.example.demo.repository.UserRepository;
import com.example.demo.security.JwtUtil;
import com.example.demo.service.Interface.LoginService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
public class LoginServiceImpl implements LoginService {

    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    public LoginServiceImpl(
            UserRepository userRepository,
            AuthenticationManager authenticationManager,
            JwtUtil jwtUtil
    ) {
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public LoginServiceResult login(
            LoginRequest request
    ) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getPassword()
                    )
            );
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException(
                    "Sai email hoặc mật khẩu"
            );
        }

        User user = userRepository
                .findByEmail(request.getEmail())
                .orElseThrow(
                        () -> new BadRequestException(
                                "Email không tồn tại"
                        )
                );

        String accessToken =
                jwtUtil.generateToken(
                        user.getEmail()
                );

        String refreshToken =
                jwtUtil.generateRefreshToken(
                        user.getEmail()
                );

        return LoginServiceResult.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .username(user.getUsername())
                .email(user.getEmail())
                .build();
    }
}