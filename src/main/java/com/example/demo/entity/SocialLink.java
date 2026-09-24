package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "social_link")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SocialLink {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(name = "platform", nullable = false)
    private String platform;
    @Column(name = "url", nullable = false)
    private String url;
    @Column(name = "icon_url", nullable = false)
    private String iconUrl;
    @Column(name = "display_order")
    private Integer displayOrder;
}
