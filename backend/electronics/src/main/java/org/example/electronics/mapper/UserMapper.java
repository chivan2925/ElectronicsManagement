package org.example.electronics.mapper;

import org.example.electronics.dto.request.admin.AdminUserRequestDTO;
import org.example.electronics.dto.response.admin.AdminUserResponseDTO;
import org.example.electronics.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

    UserEntity toEntity(AdminUserRequestDTO adminUserRequestDTO);

    AdminUserResponseDTO toResponseDTO(UserEntity userEntity);
}
