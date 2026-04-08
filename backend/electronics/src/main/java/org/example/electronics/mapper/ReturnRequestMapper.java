package org.example.electronics.mapper;

import org.example.electronics.dto.response.admin.AdminReturnRequestResponseDTO;
import org.example.electronics.entity.ReturnRequestEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ReturnRequestMapper {

    AdminReturnRequestResponseDTO toResponseDTO(ReturnRequestEntity returnRequestEntity);
}
