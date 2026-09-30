package com.example.demo.service.Implement;

import com.example.demo.Enum.RoleEnum;
import com.example.demo.dto.request.RegisterRequest;
import com.example.demo.entity.Role;
import com.example.demo.entity.User;
import com.example.demo.exception.BadRequestException;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.Interface.RegisterService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RegisterServiceImpl implements RegisterService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    public RegisterServiceImpl(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            RoleRepository roleRepository
    ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
    }

    @Override
    @Transactional
    public void registerUser(RegisterRequest request) {

        // ==============================
        // CHECK EMAIL
        // ==============================

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new BadRequestException(
                    "Email đã được sử dụng"
            );
        }

        // ==============================
        // CHECK USERNAME
        // ==============================

        if (userRepository.existsByUsername(request.getUsername())) {
            throw new BadRequestException(
                    "Username đã được sử dụng"
            );
        }

        // ==============================
        // GET USER ROLE
        // ==============================

        Role role = roleRepository
                .findByName(RoleEnum.USER)
                .orElseThrow(
                        () -> new BadRequestException(
                                "USER role does not exist"
                        )
                );

        // ==============================
        // CREATE USER
        // ==============================

        User user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(
                        passwordEncoder.encode(
                                request.getPassword()
                        )
                )
                .role(role)
                .build();

        // ==============================
        // SAVE
        // ==============================

        userRepository.save(user);
    }
}