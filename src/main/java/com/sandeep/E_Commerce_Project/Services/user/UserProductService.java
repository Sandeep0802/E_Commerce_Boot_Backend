package com.sandeep.E_Commerce_Project.Services.user;

import com.sandeep.E_Commerce_Project.Dtos.Product.ProductResponseDto;
import com.sandeep.E_Commerce_Project.Mappers.ProductMapper;
import com.sandeep.E_Commerce_Project.Models.Product;
import com.sandeep.E_Commerce_Project.Repositories.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.util.StringUtils;


import java.math.BigDecimal;
import java.util.List;

@Service
public class UserProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    private final MongoTemplate mongoTemplate;

    public UserProductService(ProductRepository productRepository,
                          ProductMapper productMapper,
                          MongoTemplate mongoTemplate) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
        this.mongoTemplate = mongoTemplate;
    }
    // 1️⃣ Get all products (with optional filters)
    public List<ProductResponseDto> getProducts(
            String category,
            BigDecimal minPrice,
            BigDecimal maxPrice,
            String search
    ) {
        Query query = new Query();

        if (StringUtils.hasText(category)) {
            query.addCriteria(Criteria.where("category").regex("^" + category + "$", "i"));
        }

        if (minPrice != null) {
            query.addCriteria(Criteria.where("price").gte(minPrice));
        }

        if (maxPrice != null) {
            query.addCriteria(Criteria.where("price").lte(maxPrice));
        }

        if (StringUtils.hasText(search)) {
            query.addCriteria(Criteria.where("name").regex(search, "i"));
        }

        List<Product> products = mongoTemplate.find(query, Product.class);

        return products.stream()
                .map(productMapper::toDto)
                .toList();
    }


    // 2️⃣ Get product by ID
    public ProductResponseDto getProductById(String id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));

        return productMapper.toDto(product);
    }
}
