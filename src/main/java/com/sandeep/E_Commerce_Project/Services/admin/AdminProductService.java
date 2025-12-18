package com.sandeep.E_Commerce_Project.Services.admin;

import com.sandeep.E_Commerce_Project.Dtos.Product.ProductRequestDto;
import com.sandeep.E_Commerce_Project.Dtos.Product.ProductResponseDto;
import com.sandeep.E_Commerce_Project.Mappers.ProductMapper;
import com.sandeep.E_Commerce_Project.Models.Product;
import com.sandeep.E_Commerce_Project.Repositories.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    public AdminProductService(ProductRepository productRepository,
                               ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
    }

    // 1️⃣ CREATE PRODUCT
    public ProductResponseDto createProduct(ProductRequestDto dto) {
        Product product = productMapper.toEntity(dto);
        Product saved = productRepository.save(product);
        return productMapper.toDto(saved);
    }

    // 2️⃣ GET ALL PRODUCTS
    public List<ProductResponseDto> getAllProducts() {
        return productRepository.findAll()
                .stream()
                .map(productMapper::toDto)
                .toList();
    }

    // 3️⃣ GET PRODUCT BY ID
    public ProductResponseDto getProductById(String id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));

        return productMapper.toDto(product);
    }

    // 4️⃣ UPDATE PRODUCT
    public ProductResponseDto updateProduct(String id, ProductRequestDto dto) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));

        productMapper.updateEntity(dto, product);
        Product updated = productRepository.save(product);

        return productMapper.toDto(updated);
    }

    // 5️⃣ DELETE PRODUCT
    public void deleteProduct(String id) {
        if (!productRepository.existsById(id)) {
            throw new IllegalArgumentException("Product not found");
        }
        productRepository.deleteById(id);
    }
}