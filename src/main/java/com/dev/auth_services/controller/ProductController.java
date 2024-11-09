package com.dev.auth_services.controller;

import com.dev.auth_services.custom.ApiResponse;
import com.dev.auth_services.entity.Product;
import com.dev.auth_services.service.AuthService;
import com.dev.auth_services.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private AuthService authService;

    @PostMapping("/add")
    @CrossOrigin(origins = {"http://localhost:3000", "http://localhost:3001"})
    public ResponseEntity<ApiResponse> addProduct(@RequestBody Product product, @RequestHeader("Authorization") String token) {
        try {
            authService.validateToken(token);  // Validate token without returning a boolean
            Product newProduct = productService.addProduct(product);
            ApiResponse response = new ApiResponse(200, true, "Product added successfully", newProduct);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ApiResponse(401, false, "Unauthorized: Invalid token"));
        }
    }

    @GetMapping("/all")
    @CrossOrigin(origins = {"http://localhost:3000", "http://localhost:3001"})
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/product/{id}")
    @CrossOrigin(origins = {"http://localhost:3000", "http://localhost:3001"})
    public ResponseEntity<ApiResponse> getProductById(@PathVariable String id) {
        try {
            Product product = productService.getProductById(id);
            if (product != null) {
                return ResponseEntity.ok(new ApiResponse(200, true, "Product found", product));
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse(404, false, "Product not found"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(500, false, "Internal Server Error"));
        }
    }

    @PutMapping("/update/{id}")
    @CrossOrigin(origins = {"http://localhost:3000", "http://localhost:3001"})
    public ResponseEntity<ApiResponse> updateProduct(@PathVariable String id, @RequestBody Product productDetails, @RequestHeader("Authorization") String token) {
        try {
            authService.validateToken(token);  // Validate token without returning a boolean
            Product updatedProduct = productService.updateProduct(id, productDetails);
            if (updatedProduct != null) {
                return ResponseEntity.ok(new ApiResponse(200, true, "Product updated successfully", updatedProduct));
            }else{
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ApiResponse(404, false, "Product not found"));
            }

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ApiResponse(401, false, "Unauthorized: Invalid token"));
        }
    }

    @DeleteMapping("/delete/{id}")
    @CrossOrigin(origins = {"http://localhost:3000", "http://localhost:3001"})
    public ResponseEntity<ApiResponse> deleteProduct(@PathVariable String id, @RequestHeader("Authorization") String token) {
        try {
            authService.validateToken(token);  // Validate token without returning a boolean
            productService.deleteProduct(id);
            return ResponseEntity.ok(new ApiResponse(200, true, "Product deleted successfully"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ApiResponse(401, false, "Unauthorized: Invalid token"));
        }
    }
}