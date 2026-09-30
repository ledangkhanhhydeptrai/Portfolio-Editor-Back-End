package com.example.demo.repository;

import com.example.demo.entity.SocialLink;
import com.example.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface SocialLinkRepository extends JpaRepository<SocialLink, UUID> {
    List<SocialLink> findAllByOrderByDisplayOrderAsc();

    List<SocialLink> findAllByUserOrderByDisplayOrderAsc(User user);

    Optional<SocialLink> findByIdAndUser(UUID id, User user);
}
