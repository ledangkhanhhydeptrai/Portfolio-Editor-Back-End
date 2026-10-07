package com.example.demo.repository;

import com.example.demo.entity.Direction;
import com.example.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface DirectionRepository
        extends JpaRepository<Direction, UUID> {

    List<Direction> findAllByOrderByDisplayOrderAsc();

    List<Direction> findAllByUserOrderByDisplayOrderAsc(User user);
    Optional<Direction> findFirstByUser_EmailOrderByDisplayOrderAsc(String email);
    Optional<Direction> findByIdAndUser(UUID id, User user);
}