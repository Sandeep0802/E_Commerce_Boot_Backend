package com.sandeep.E_Commerce_Project.Repositories;


import com.sandeep.E_Commerce_Project.Models.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.math.BigDecimal;
import java.util.List;

public interface ProductRepository extends MongoRepository<Product, String> {

    List<Product> findByCategoryIgnoreCase(String category);

    List<Product> findByPriceBetween(BigDecimal min, BigDecimal max);

    List<Product> findByPriceGreaterThanEqual(BigDecimal min);

    List<Product> findByPriceLessThanEqual(BigDecimal max);

    List<Product> findByNameContainingIgnoreCase(String keyword);

    List<Product> findByCategoryIgnoreCaseAndPriceBetween(
            String category,
            BigDecimal min,
            BigDecimal max
    );
}
