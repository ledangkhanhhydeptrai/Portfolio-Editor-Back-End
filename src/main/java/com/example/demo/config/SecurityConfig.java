package com.example.demo.config;

import com.example.demo.security.CustomAccessDeniedHandler;
import com.example.demo.security.CustomAuthenticationEntryPoint;
import com.example.demo.security.JwtAuthenticationFilter;
import com.example.demo.security.JwtUtil;
import com.example.demo.service.Implement.UserDetailsServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    private final JwtUtil jwtUtil;

    private final UserDetailsServiceImpl userDetailsService;

    private final CustomAuthenticationEntryPoint
            customAuthenticationEntryPoint;

    private final CustomAccessDeniedHandler
            customAccessDeniedHandler;

    public SecurityConfig(
            JwtUtil jwtUtil,
            UserDetailsServiceImpl userDetailsService,
            CustomAuthenticationEntryPoint customAuthenticationEntryPoint,
            CustomAccessDeniedHandler customAccessDeniedHandler
    ) {
        this.jwtUtil =
                jwtUtil;

        this.userDetailsService =
                userDetailsService;

        this.customAuthenticationEntryPoint =
                customAuthenticationEntryPoint;

        this.customAccessDeniedHandler =
                customAccessDeniedHandler;
    }

    @Bean
    public JwtAuthenticationFilter
    jwtAuthenticationFilter() {

        return new JwtAuthenticationFilter(
                jwtUtil,
                userDetailsService
        );
    }

    @Bean
    public SecurityFilterChain filterChain(
            HttpSecurity http
    ) throws Exception {

        http
                .cors(
                        Customizer.withDefaults()
                )

                .csrf(
                        csrf ->
                                csrf.disable()
                )

                .sessionManagement(
                        session ->
                                session.sessionCreationPolicy(
                                        SessionCreationPolicy.STATELESS
                                )
                )

                .exceptionHandling(
                        exception ->
                                exception
                                        .authenticationEntryPoint(
                                                customAuthenticationEntryPoint
                                        )
                                        .accessDeniedHandler(
                                                customAccessDeniedHandler
                                        )
                )

                .authorizeHttpRequests(
                        auth ->
                                auth
                                        .requestMatchers(
                                                "/swagger-ui/**",
                                                "/v3/api-docs/**",
                                                "/api/auth/**",
                                                "/api/public/**"
                                        )
                                        .permitAll()

                                        .requestMatchers(
                                                "/api/**"
                                        )
                                        .authenticated()

                                        .anyRequest()
                                        .permitAll()
                )

                .addFilterBefore(
                        jwtAuthenticationFilter(),
                        UsernamePasswordAuthenticationFilter.class
                );

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager
    authenticationManager(
            AuthenticationConfiguration config
    ) throws Exception {

        return config.getAuthenticationManager();
    }
}