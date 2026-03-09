package org.example.electronics.mapper;

import org.example.electronics.dto.request.AddressRequestDTO;
import org.example.electronics.dto.response.AddressResponseDTO;
import org.example.electronics.entity.AddressEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AddressMapper {

    AddressEntity toEntity(AddressRequestDTO addressRequestDTO);

    AddressResponseDTO toResponseDTO(AddressEntity addressEntity);
}
