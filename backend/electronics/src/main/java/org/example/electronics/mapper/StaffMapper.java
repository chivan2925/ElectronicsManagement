package org.example.electronics.mapper;

import org.example.electronics.dto.request.admin.AdminStaffRequestDTO;
import org.example.electronics.dto.response.admin.AdminStaffResponseDTO;
import org.example.electronics.entity.StaffEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface StaffMapper {

    @Mapping(target = "role", ignore = true)
    @Mapping(target = "hashedPassword", ignore = true)
    StaffEntity toEntity(AdminStaffRequestDTO adminStaffRequestDTO);

    @Mapping(source = "role.id", target = "roleId")
    AdminStaffResponseDTO toResponseDTO(StaffEntity staffEntity);

    @Mapping(target = "role", ignore = true)
    @Mapping(target = "hashedPassword", ignore = true)
    void updateEntityFromDTO(AdminStaffRequestDTO adminStaffRequestDTO,
                             @MappingTarget StaffEntity staffEntity);
}
