package com.example.demo.repository;

import com.example.demo.entity.Skill;
import com.example.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface SkillRepository extends JpaRepository<Skill, UUID> {
    List<Skill> findAllByOrderByDisplayOrderAsc();

    boolean existsByUserAndNameIgnoreCase(User user, String name);

    List<Skill> findAllByUserOrderByDisplayOrderAsc(User user);

    Optional<Skill> findByIdAndUser(UUID id, User user);
}
