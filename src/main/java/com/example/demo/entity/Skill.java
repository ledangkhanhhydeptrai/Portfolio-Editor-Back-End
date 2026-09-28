package com.example.demo.entity;

import com.example.demo.Enum.SkillEnum;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "skill")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Skill {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(name = "name", nullable = false)
    private String name;
    @Enumerated(EnumType.STRING)
    @Column(name = "category", nullable = false)
    private SkillEnum category;
    @Column(name = "icon_url")
    private String iconUrl;
    @Column(name = "display_order")
    private Integer displayOrder;
}
