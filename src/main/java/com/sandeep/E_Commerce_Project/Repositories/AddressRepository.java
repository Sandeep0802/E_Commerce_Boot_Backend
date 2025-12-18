package com.sandeep.E_Commerce_Project.Repositories;

import com.sandeep.E_Commerce_Project.Models.Address;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface AddressRepository extends MongoRepository<Address, String> {

    List<Address> findByUserId(String userId);

    Optional<Address> findByUserIdAndDefaultAddressTrue(String userId);
}
