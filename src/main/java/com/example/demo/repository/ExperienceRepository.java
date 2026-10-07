package com.example.demo.repository;

import com.example.demo.entity.Experience;
import com.example.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ExperienceRepository extends JpaRepository<Experience, UUID> {
    List<Experience> findAllByUserOrderByDisplayOrderAsc(User user);

    Optional<Experience> findFirstByUser_EmailOrderByDisplayOrderAsc(String email);

    Optional<Experience> findByIdAndUser(UUID id, User user);
}
