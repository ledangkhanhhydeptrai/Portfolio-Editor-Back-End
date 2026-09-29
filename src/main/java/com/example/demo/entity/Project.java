package com.example.demo.entity;

import com.example.demo.Enum.SkillEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Table(name = "Project")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(nullable = false)
    private String title;
    @Column(columnDefinition = "TEXT")
    private String description;
    @Column(name = "thumbnail_url")
    private String thumbnailUrl;
    @Column(name = "github_url")
    private String githubUrl;
    @Column(name = "demo_url")
    private String demoUrl;
    @Column(name = "display_order")
    private Integer displayOrder;
    private Boolean featured;
    @Enumerated(EnumType.STRING)
    @Column(name = "category")
    private SkillEnum category;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "user_id",
            nullable = false
    )
    private User user;
}
