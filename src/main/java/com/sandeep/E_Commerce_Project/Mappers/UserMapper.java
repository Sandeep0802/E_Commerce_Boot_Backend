package com.sandeep.E_Commerce_Project.Mappers;

import com.sandeep.E_Commerce_Project.Dtos.admin.AdminUserResponseDto;
import com.sandeep.E_Commerce_Project.Models.Role;
import com.sandeep.E_Commerce_Project.Models.User;
import com.sandeep.E_Commerce_Project.Repositories.OrderRepository;
import org.mapstruct.*;
import java.util.Set;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "role", source = "roles", qualifiedByName = "mapRole")
    AdminUserResponseDto toDto(User user, @Context OrderRepository orderRepository);

    @AfterMapping
    default void setTotalOrders(User user, @MappingTarget AdminUserResponseDto dto, @Context OrderRepository orderRepository) {
        if (user.getId() != null) {
            dto.setTotalOrders(orderRepository.findByUserId(user.getId()).size());
        }
    }

    @Named("mapRole")
    default String mapRole(Set<Role> roles) {
        if (roles == null || roles.isEmpty()) return "USER";
        return roles.contains(Role.ROLE_ADMIN) ? "ADMIN" : "USER";
    }
}