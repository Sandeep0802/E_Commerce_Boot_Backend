package com.sandeep.E_Commerce_Project.Controllers.address;

import com.sandeep.E_Commerce_Project.Dtos.address.AddressRequestDto;
import com.sandeep.E_Commerce_Project.Dtos.address.AddressResponseDto;
import com.sandeep.E_Commerce_Project.Exceptions.ApiResponse;
import com.sandeep.E_Commerce_Project.Services.address.AddressService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/addresses")
public class AddressController {

    private final AddressService addressService;

    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    // 🔹 Add address
    @PostMapping
    public ResponseEntity<ApiResponse<AddressResponseDto>> addAddress(
            @Valid @RequestBody AddressRequestDto dto,
            Authentication auth,
            HttpServletRequest request
    ) {
        return ResponseEntity.ok(
                ApiResponse.success(
                        "Address added successfully",
                        addressService.addAddress(auth.getName(), dto),
                        request.getRequestURI(),
                        201
                )
        );
    }

    // 🔹 Get all addresses
    @GetMapping
    public ResponseEntity<ApiResponse<List<AddressResponseDto>>> getAddresses(
            Authentication auth,
            HttpServletRequest request
    ) {
        return ResponseEntity.ok(
                ApiResponse.success(
                        "Addresses fetched",
                        addressService.getAddresses(auth.getName()),
                        request.getRequestURI(),
                        200
                )
        );
    }

    // 🔹 Update address
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<AddressResponseDto>> updateAddress(
            @PathVariable String id,
            @Valid @RequestBody AddressRequestDto dto,
            Authentication auth,
            HttpServletRequest request
    ) {
        return ResponseEntity.ok(
                ApiResponse.success(
                        "Address updated successfully",
                        addressService.updateAddress(auth.getName(), id, dto),
                        request.getRequestURI(),
                        200
                )
        );
    }

    // 🔹 Delete address
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteAddress(
            @PathVariable String id,
            Authentication auth,
            HttpServletRequest request
    ) {
        addressService.deleteAddress(auth.getName(), id);

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Address deleted successfully",
                        null,
                        request.getRequestURI(),
                        200
                )
        );
    }
}
