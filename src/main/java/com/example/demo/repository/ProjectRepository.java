package com.example.demo.repository;

import com.example.demo.entity.Project;
import com.example.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProjectRepository extends JpaRepository<Project, UUID> {
    List<Project> findAllByOrderByDisplayOrderAsc();

    List<Project> findAllByUserOrderByDisplayOrderAsc(User user);

    Optional<Project> findFirstByUser_EmailOrderByDisplayOrderAsc(String email);

    Optional<Project> findByIdAndUser(UUID id, User user);
}
