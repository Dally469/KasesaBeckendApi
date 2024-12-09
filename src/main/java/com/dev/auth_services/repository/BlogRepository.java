package com.dev.auth_services.repository;

import com.dev.auth_services.entity.Blog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BlogRepository extends JpaRepository<Blog, UUID> {
    // Additional custom query methods (if needed)
}