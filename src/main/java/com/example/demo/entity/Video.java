package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "videos")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Video {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(
            name = "title",
            nullable = false
    )
    private String title;

    @Column(
            name = "description",
            columnDefinition = "TEXT"
    )
    private String description;

    @Column(
            name = "video_url",
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String videoUrl;

    @Column(
            name = "thumbnail_url",
            columnDefinition = "TEXT"
    )
    private String thumbnailUrl;

    @Column(
            name = "category",
            nullable = false
    )
    private String category;

    @Column(name = "duration")
    private String duration;

    @Column(name = "year")
    private Integer year;

    @Column(name = "display_order")
    private Integer displayOrder;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "user_id",
            nullable = false
    )
    private User user;
}