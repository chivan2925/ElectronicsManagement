package org.example.electronics.mapper;

import org.example.electronics.dto.response.admin.AdminAddressResponseDTO;
import org.example.electronics.entity.AddressEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AddressMapper {

    AdminAddressResponseDTO toResponseDTO(AddressEntity addressEntity);
}
