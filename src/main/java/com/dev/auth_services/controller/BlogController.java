package com.dev.auth_services.controller;

import com.dev.auth_services.custom.ApiResponse;
import com.dev.auth_services.entity.Blog;
import com.dev.auth_services.service.AuthService;
import com.dev.auth_services.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/blogs")
public class BlogController {

    @Autowired
    private BlogService blogService;

    @Autowired
    private AuthService authService;


    @GetMapping("/all")
    public ResponseEntity<ApiResponse> getAllBlogs() {
        try {
            List<Blog> blogs = blogService.getAllBlogs();
            return ResponseEntity.ok(new ApiResponse(200, true, "Blogs retrieved successfully", blogs));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(500, false, "Internal Server Error"));
        }
    }

    @GetMapping("/blog/{id}")
    public ResponseEntity<ApiResponse> getBlogById(@PathVariable String id) {
        try {
            Blog blog = blogService.getBlogById(id);
            return ResponseEntity.ok(new ApiResponse(200, true, "Blog found", blog));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse(404, false, "Blog not found"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(500, false, "Internal Server Error"));
        }
    }

    @PostMapping("/create")
    public ResponseEntity<ApiResponse> createBlog(@RequestBody Blog blog, @RequestHeader("Authorization") String token) {
        try {
            authService.validateToken(token);  // Validate the token

            Blog createdBlog = blogService.createBlog(blog);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ApiResponse(200, true, "Blog created successfully", createdBlog));

        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ApiResponse(401, false, "Unauthorized: Invalid token"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(500, false, "Failed to create blog"));
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse> updateBlog(@PathVariable String id, @RequestBody Blog blog, @RequestHeader("Authorization") String token) {
        try {
            authService.validateToken(token);  // Validate the token

            Blog updatedBlog = blogService.updateBlog(id, blog);
            return ResponseEntity.ok(new ApiResponse(200, true, "Blog updated successfully", updatedBlog));

        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse(404, false, "Blog not found"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ApiResponse(401, false, "Unauthorized: Invalid token"));
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse> deleteBlog(@PathVariable String id, @RequestHeader("Authorization") String token) {
        try {
            authService.validateToken(token);  // Validate the token

            blogService.deleteBlog(id);
            return ResponseEntity.ok(new ApiResponse(200, true, "Blog deleted successfully"));

        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse(404, false, "Blog not found"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ApiResponse(401, false, "Unauthorized: Invalid token"));
        }
    }
}
