package com.example.demo.repository;

import com.example.demo.entity.CV;
import com.example.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CVRepository
        extends JpaRepository<CV, UUID> {

    List<CV> findAllByOrderByDisplayOrderAsc();

    Optional<CV> findByIdAndUser(
            UUID id,
            User user
    );

    List<CV> findAllByUserOrderByDisplayOrderAsc(User user);
}
