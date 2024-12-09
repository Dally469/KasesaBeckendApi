package com.dev.auth_services.repository;


import com.dev.auth_services.entity.Gallery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface GalleryRepository extends JpaRepository<Gallery, UUID> {
}
