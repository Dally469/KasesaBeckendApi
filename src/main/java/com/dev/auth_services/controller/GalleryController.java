package com.dev.auth_services.controller;

import com.dev.auth_services.custom.ApiResponse;
import com.dev.auth_services.entity.Gallery;
import com.dev.auth_services.entity.Product;
import com.dev.auth_services.service.AuthService;
import com.dev.auth_services.service.GalleryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/gallery")
public class GalleryController {

    @Autowired
    private GalleryService galleryService;

    @Autowired
    AuthService authService;


    // Create: Upload multiple images
    @PostMapping("/bulks")
     public ResponseEntity<ApiResponse> createBlogs(@RequestBody List<Gallery> galleries, @RequestHeader("Authorization") String token) {
        try {
            // Validate token
            authService.validateToken(token);
            // Create multiple blogs
            List<Gallery> createdBlogs = galleryService.saveImages(galleries);

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ApiResponse(201, true, "Gallery created successfully", createdBlogs));

        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse(404, false, "Gallery not found"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ApiResponse(401, false, "Unauthorized: Invalid token"));
        }
    }

    // Read: Get all images
    @GetMapping("/all")
    public ResponseEntity<ApiResponse> getAllImages() {
        List<Gallery> images = galleryService.getAllImages();
        ApiResponse response = new ApiResponse(200, true, "Fetched all images", images);
        return ResponseEntity.ok(response);
    }

    // Update: Update an image's URL by ID
    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse> updateImage(@PathVariable String id, @RequestBody Gallery gallery, @RequestHeader("Authorization") String token) {
        try {
            // Validate token
            authService.validateToken(token);
             Gallery updatedImage = galleryService.updateImageUrl(id, gallery);
            ApiResponse response = new ApiResponse(200, true, "Image updated successfully", updatedImage);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse(404, false, "Gallery not found"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ApiResponse(401, false, "Unauthorized: Invalid token"));
        }
    }

    // Delete: Delete an image by ID
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse> deleteImage(@PathVariable UUID id) {
        try {
            galleryService.deleteImage(id);
            ApiResponse response = new ApiResponse(200, true, "Image deleted successfully");
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            ApiResponse response = new ApiResponse(404, false, "Image not found or failed to delete");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    // Dummy method to represent Firebase upload functionality
    private String uploadToFirebase(MultipartFile file) throws IOException {
        return "https://firebase.storage.example.com/" + file.getOriginalFilename();
    }

}
