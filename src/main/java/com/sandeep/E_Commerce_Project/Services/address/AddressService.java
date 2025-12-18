package com.sandeep.E_Commerce_Project.Services.address;

import com.sandeep.E_Commerce_Project.Dtos.address.AddressRequestDto;
import com.sandeep.E_Commerce_Project.Dtos.address.AddressResponseDto;
import com.sandeep.E_Commerce_Project.Exceptions.ResourceNotFoundException;
import com.sandeep.E_Commerce_Project.Mappers.AddressMapper;
import com.sandeep.E_Commerce_Project.Models.Address;
import com.sandeep.E_Commerce_Project.Repositories.AddressRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressService {

    private final AddressRepository addressRepository;
    private final AddressMapper addressMapper;

    public AddressService(AddressRepository addressRepository,
                          AddressMapper addressMapper) {
        this.addressRepository = addressRepository;
        this.addressMapper = addressMapper;
    }

    // 🔹 Add address
    public AddressResponseDto addAddress(String userId, AddressRequestDto dto) {

        if (dto.isDefaultAddress()) {
            unsetDefaultAddress(userId);
        }

        Address address = addressMapper.toEntity(dto);
        address.setUserId(userId);

        return addressMapper.toDto(addressRepository.save(address));
    }

    // 🔹 Get all addresses
    public List<AddressResponseDto> getAddresses(String userId) {
        return addressRepository.findByUserId(userId)
                .stream()
                .map(addressMapper::toDto)
                .toList();
    }

    // 🔹 Update address
    public AddressResponseDto updateAddress(String userId, String addressId, AddressRequestDto dto) {

        Address address = addressRepository.findById(addressId)
                .filter(a -> a.getUserId().equals(userId))
                .orElseThrow(() -> new ResourceNotFoundException("Address not found"));

        if (dto.isDefaultAddress()) {
            unsetDefaultAddress(userId);
        }

        addressMapper.updateEntity(dto, address);
        return addressMapper.toDto(addressRepository.save(address));
    }

    // 🔹 Delete address
    public void deleteAddress(String userId, String addressId) {

        Address address = addressRepository.findById(addressId)
                .filter(a -> a.getUserId().equals(userId))
                .orElseThrow(() -> new ResourceNotFoundException("Address not found"));

        addressRepository.delete(address);
    }

    // 🔹 Unset default address (UPDATED QUERY)
    private void unsetDefaultAddress(String userId) {
        addressRepository.findByUserIdAndDefaultAddressTrue(userId)
                .ifPresent(addr -> {
                    addr.setDefaultAddress(false);
                    addressRepository.save(addr);
                });
    }
}
