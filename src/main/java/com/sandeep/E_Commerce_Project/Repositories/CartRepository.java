package com.sandeep.E_Commerce_Project.Repositories;

import com.sandeep.E_Commerce_Project.Models.Cart;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface CartRepository extends MongoRepository<Cart, String> {

    Optional<Cart> findByUserId(String userId);
    void deleteByUserId(String userId);

}
