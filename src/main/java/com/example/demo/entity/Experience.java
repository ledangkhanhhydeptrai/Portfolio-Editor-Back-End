package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Table(name = "experience")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Experience {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(name = "company_name", nullable = false)
    private String companyName;
    @Column(name = "position", nullable = false)
    private String position;
    @Column(name = "description", columnDefinition = "TEXT")
    private String description;
    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;
    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;
    @Column(name = "is_current")
    private Boolean isCurrent;
    @Column(name = "display_order")
    private Integer displayOrder;
}
