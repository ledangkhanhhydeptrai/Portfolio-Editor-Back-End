package com.example.demo.entity;

import com.example.demo.Enum.RoleEnum;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "roles")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Enumerated(EnumType.STRING)
    @Column(
            name = "name",
            nullable = false,
            unique = true
    )
    private RoleEnum name;

    @OneToMany(
            mappedBy = "role"
    )
    @Builder.Default
    private List<User> users =
            new ArrayList<>();
}