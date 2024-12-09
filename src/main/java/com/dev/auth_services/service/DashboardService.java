package com.dev.auth_services.service;

import com.dev.auth_services.repository.*;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.Map;

@Service
public class DashboardService {

    private final TeamRepository teamRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final GalleryRepository galleryRepository;
    private final BlogRepository blogRepository;

    public DashboardService(TeamRepository teamRepository, UserRepository userRepository, ProductRepository productRepository, GalleryRepository galleryRepository, BlogRepository blogRepository) {
        this.teamRepository = teamRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.galleryRepository = galleryRepository;
        this.blogRepository = blogRepository;
    }

    public Map<String, Long> getDashboardSummary() {
        Map<String, Long> summary = new HashMap<>();
        summary.put("totalTeams", teamRepository.count());
        summary.put("totalUsers", userRepository.count());
        summary.put("totalProducts", productRepository.count());
        summary.put("totalPhotos", galleryRepository.count());
        summary.put("totalBlogs", blogRepository.count());
        return summary;
    }
}
