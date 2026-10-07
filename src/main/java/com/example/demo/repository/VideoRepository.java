package com.example.demo.repository;

import com.example.demo.entity.User;
import com.example.demo.entity.Video;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface VideoRepository extends JpaRepository<Video, UUID> {
    boolean existsByUserAndTitleIgnoreCase(
            User user,
            String title
    );
    Optional<Video> findFirstByUser_EmailOrderByDisplayOrderAsc(String email);
    List<Video> findAllByUserOrderByDisplayOrderAsc(User user);

    Optional<Video> findByIdAndUser(UUID id, User user);
}
