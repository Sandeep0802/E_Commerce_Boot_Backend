package com.sandeep.E_Commerce_Project.Dtos.address;

import lombok.Data;

@Data
public class AddressResponseDto {

    private String id;

    private String fullName;
    private String phone;

    private String street;
    private String city;
    private String state;
    private String postalCode;
    private String country;

    private boolean defaultAddress;
}
