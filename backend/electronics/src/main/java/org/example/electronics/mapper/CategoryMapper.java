package org.example.electronics.mapper;

import org.example.electronics.dto.request.admin.AdminCategoryRequestDTO;
import org.example.electronics.dto.response.admin.AdminCategoryResponseDTO;
import org.example.electronics.entity.CategoryEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CategoryMapper {

    @Mapping(source = "parentId", target = "parent.id")
    CategoryEntity toEntity(AdminCategoryRequestDTO adminCategoryRequestDTO);

    @Mapping(source = "parent.id", target = "parentId")
    @Mapping(source = "parent.name", target = "parentName")
    AdminCategoryResponseDTO toResponseDTO(CategoryEntity categoryEntity);

    @Mapping(target = "parent", ignore = true)
    @Mapping(target = "subCategoryList", ignore = true)
    void updateEntityFromDTO(AdminCategoryRequestDTO adminCategoryRequestDTO,
                             @MappingTarget CategoryEntity categoryEntity);
}
