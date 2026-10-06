package com.example.demo.repository;

import com.example.demo.entity.User;
import com.example.demo.entity.WorkStyle;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface WorkStyleRepository
        extends JpaRepository<WorkStyle, UUID> {

    List<WorkStyle> findAllByOrderByDisplayOrderAsc();

    List<WorkStyle> findAllByUserOrderByDisplayOrderAsc(
            User user
    );

    Optional<WorkStyle> findByIdAndUser(
            UUID id,
            User user
    );
}