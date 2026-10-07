package com.example.demo.repository;

import com.example.demo.entity.Education;
import com.example.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface EducationRepository extends JpaRepository<Education, UUID> {
    List<Education> findAllByUserOrderByDisplayOrderAsc(User user);

    Optional<Education> findFirstByUser_EmailOrderByDisplayOrderAsc(String email);

    Optional<Education> findByIdAndUserOrderByDisplayOrderAsc(UUID id, User user);
}
