package com.sandeep.E_Commerce_Project.Mappers;

import com.sandeep.E_Commerce_Project.Dtos.address.AddressRequestDto;
import com.sandeep.E_Commerce_Project.Dtos.address.AddressResponseDto;
import com.sandeep.E_Commerce_Project.Models.Address;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface AddressMapper {

    // Request → Entity
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "userId", ignore = true)
    Address toEntity(AddressRequestDto dto);

    // Entity → Response
    AddressResponseDto toDto(Address address);

    // Update existing entity (ignore nulls)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "userId", ignore = true)
    void updateEntity(AddressRequestDto dto, @MappingTarget Address address);
}
