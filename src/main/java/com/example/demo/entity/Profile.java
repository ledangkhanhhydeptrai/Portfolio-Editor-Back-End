package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Table(name = "profile")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Profile {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(name = "full_name", nullable = false)
    private String fullName;
    @Column(name = "job_title", nullable = false)
    private String jobTitle;
    @Column(name = "short_description", nullable = false, columnDefinition = "TEXT")
    private String shortDescription;
    @Column(name = "about_me", columnDefinition = "TEXT")
    private String aboutMe;
    @Column(name = "avatar_url")
    private String avatarUrl;
    @Column(name = "cv_url")
    private String cvUrl;
    @Column(name = "email")
    private String email;
    @Column(name = "phone")
    private String phone;
    @Column(name = "location")
    private String location;
}
