package com.dev.auth_services.service;

import com.dev.auth_services.entity.Gallery;
import com.dev.auth_services.entity.Product;
import com.dev.auth_services.entity.Team;
import com.dev.auth_services.repository.GalleryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class GalleryService {

    @Autowired
    private GalleryRepository galleryRepository;

    // Method to save multiple images
    public List<Gallery> saveImages(List<Gallery> galleries) {
        return galleryRepository.saveAll(galleries);
    }


    // Get all images
    public List<Gallery> getAllImages() {
        return galleryRepository.findAll();
    }

    // Update image URL by ID
    public Gallery updateImageUrl(String id, Gallery galleryDetails) {
        Gallery gallery = galleryRepository.findById(UUID.fromString(id))
                .orElseThrow(() -> new RuntimeException("Team not found"));


        if (galleryDetails.getImageUrl() != null) {
            gallery.setImageUrl(galleryDetails.getImageUrl());
        }
        if (galleryDetails.getName() != null) {
            gallery.setName(galleryDetails.getName());
        }
            return galleryRepository.save(gallery);

    }

    // Delete image by ID
    public void deleteImage(UUID id) {
        if (galleryRepository.existsById(id)) {
            galleryRepository.deleteById(id);
        } else {
            throw new RuntimeException("Gallery not found");
        }
    }
}
