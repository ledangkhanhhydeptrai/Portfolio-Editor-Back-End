package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "education")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Education {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(name = "school_name", nullable = false)
    private String schoolName;
    @Column(name = "major", nullable = false)
    private String major;
    @Column(name = "degree", nullable = false)
    private String degree;
    @Column(name = "start_year", nullable = false)
    private Integer startYear;
    @Column(name = "end_year", nullable = false)
    private Integer endYear;
    @Column(columnDefinition = "TEXT")
    private String description;
    @Column(name = "display_order")
    private Integer displayOrder;
}
