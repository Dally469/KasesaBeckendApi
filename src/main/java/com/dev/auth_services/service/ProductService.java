package com.dev.auth_services.service;

import com.dev.auth_services.entity.Product;
import com.dev.auth_services.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public Product addProduct(Product product) {
        return productRepository.save(product);
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product getProductById(String id) {
        Optional<Product> product = productRepository.findById(UUID.fromString(id));
        return product.orElse(null);
    }

    public Product updateProduct(String id, Product productDetails) {
        Optional<Product> optionalProduct = productRepository.findById(UUID.fromString(id));
        if (optionalProduct.isPresent()) {
            Product product = optionalProduct.get();

            // Check and update only fields that are present in the request body
            if (productDetails.getName() != null) {
                product.setName(productDetails.getName());
            }
            if (productDetails.getDescription() != null) {
                product.setDescription(productDetails.getDescription());
            }
            if (productDetails.getPrice() != 0) {
                product.setPrice(productDetails.getPrice());
            }
            if (productDetails.getImageUrl() != null) {
                product.setImageUrl(productDetails.getImageUrl());
            }

            return productRepository.save(product);
        }
        return null;
    }

    public void deleteProduct(String id) {
        productRepository.deleteById(UUID.fromString(id));
    }
}