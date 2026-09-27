package com.example.demo.config;

import com.example.demo.Enum.RoleEnum;
import com.example.demo.entity.Role;
import com.example.demo.entity.User;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.UserRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class DataInitializer
        implements CommandLineRunner {

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(
            String... args
    ) {

        // =========================
        // ROLE INIT
        // =========================

        Role adminRole =
                createRole(
                        RoleEnum.ADMIN
                );

        createRole(
                RoleEnum.USER
        );

        // =========================
        // ADMIN USER INIT
        // =========================

        userRepository
                .findByEmail(
                        "admin@gmail.com"
                )
                .orElseGet(
                        () -> {

                            User admin =
                                    User.builder()
                                            .username("admin")
                                            .email(
                                                    "admin@gmail.com"
                                            )
                                            .password(
                                                    passwordEncoder.encode(
                                                            "123456"
                                                    )
                                            )
                                            .role(adminRole)
                                            .build();

                            return userRepository.save(
                                    admin
                            );
                        }
                );
    }

    private Role createRole(
            RoleEnum name
    ) {

        return roleRepository
                .findByName(name)
                .orElseGet(
                        () -> {

                            Role role =
                                    Role.builder()
                                            .name(name)
                                            .build();

                            return roleRepository.save(
                                    role
                            );
                        }
                );
    }
}