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

@Service
public class RegisterServiceImpl
        implements RegisterService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    public RegisterServiceImpl(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            RoleRepository roleRepository
    ) {
        this.userRepository =
                userRepository;

        this.passwordEncoder =
                passwordEncoder;
        this.roleRepository =
                roleRepository;
    }

    @Override
    public void registerUser(
            RegisterRequest request
    ) {
        Role role = roleRepository.findByName(RoleEnum.USER)
                .orElseThrow(() -> new BadRequestException("USER role does not exist"));
        User user =
                User.builder()
                        .username(
                                request.getUsername()
                        )
                        .email(
                                request.getEmail()
                        )
                        .password(
                                passwordEncoder.encode(
                                        request.getPassword()
                                )
                        )
                        .role(role)
                        .build();

        userRepository.save(user);
    }
}