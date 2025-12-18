package com.sandeep.E_Commerce_Project.Mappers;

import com.sandeep.E_Commerce_Project.Dtos.order.OrderResponseDto;
import com.sandeep.E_Commerce_Project.Models.Order;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    OrderResponseDto toDto(Order order);
}
