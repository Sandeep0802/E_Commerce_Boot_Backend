package com.sandeep.E_Commerce_Project.Models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "addresses")
public class Address {

    @Id
    private String id;

    private String userId;

    private String fullName;
    private String phone;

    private String street;
    private String city;
    private String state;
    private String postalCode;
    private String country;

    private boolean defaultAddress;
}
