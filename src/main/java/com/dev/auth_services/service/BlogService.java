package com.dev.auth_services.service;

import com.dev.auth_services.entity.Blog;
import com.dev.auth_services.entity.Product;
import com.dev.auth_services.repository.BlogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class BlogService {

    @Autowired
    private BlogRepository blogRepository;

    public List<Blog> getAllBlogs() {
        return blogRepository.findAll();
    }

    public Blog getBlogById(String id) {
        return blogRepository.findById(UUID.fromString(id)).orElseThrow(() -> new RuntimeException("Blog not found with id: " + id));
    }

    public Blog createBlog(Blog blog) {
        blog.setCreatedAt(LocalDateTime.now());
        return blogRepository.save(blog);
    }

    public Blog updateBlog(String id, Blog updatedBlog) {
        Blog existingBlog = getBlogById(id);

        // Update only fields that are non-null in the request body
        if (updatedBlog.getTitle() != null) {
            existingBlog.setTitle(updatedBlog.getTitle());
        }
        if (updatedBlog.getSubtitle() != null) {
            existingBlog.setSubtitle(updatedBlog.getSubtitle());
        }
        if (updatedBlog.getImage() != null) {
            existingBlog.setImage(updatedBlog.getImage());
        }
        if (updatedBlog.getDescription() != null) {
            existingBlog.setDescription(updatedBlog.getDescription());
        }
        if (updatedBlog.getCreatedBy() != null) {
            existingBlog.setCreatedBy(updatedBlog.getCreatedBy());
        }
        if (updatedBlog.getStatus() != null) {
            existingBlog.setStatus(updatedBlog.getStatus());
        }

        // Save and return the updated entity
        return blogRepository.save(existingBlog);
    }

    public void deleteBlog(String id) {
        Blog existingBlog = getBlogById(id);
        blogRepository.delete(existingBlog);
    }
}
