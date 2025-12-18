package com.sandeep.E_Commerce_Project.Dtos.address;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class AddressRequestDto {

    @NotBlank
    private String fullName;

    @NotBlank
    @Pattern(regexp = "^[0-9]{10}$", message = "Invalid phone number")
    private String phone;

    @NotBlank
    private String street;

    @NotBlank
    private String city;

    @NotBlank
    private String state;

    @NotBlank
    private String postalCode;

    @NotBlank
    private String country;

    private boolean defaultAddress;
}
