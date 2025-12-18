package com.sandeep.E_Commerce_Project.Mappers;

import com.sandeep.E_Commerce_Project.Dtos.Product.ProductRequestDto;
import com.sandeep.E_Commerce_Project.Dtos.Product.ProductResponseDto;
import com.sandeep.E_Commerce_Project.Models.Product;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    Product toEntity(ProductRequestDto dto);

    ProductResponseDto toDto(Product product);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntity(ProductRequestDto dto, @MappingTarget Product product);
}