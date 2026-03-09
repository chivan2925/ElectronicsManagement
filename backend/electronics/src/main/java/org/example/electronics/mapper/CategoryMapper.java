package org.example.electronics.mapper;

import org.example.electronics.dto.request.CategoryRequestDTO;
import org.example.electronics.dto.response.CategoryResponseDTO;
import org.example.electronics.entity.CategoryEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CategoryMapper {

    @Mapping(source = "parentId", target = "parent.id")
    CategoryEntity toEntity(CategoryRequestDTO categoryRequestDTO);

    @Mapping(source = "parent.id", target = "parentId")
    CategoryResponseDTO toResponseDTO(CategoryEntity categoryEntity);

    void updateEntityFromDTO(CategoryRequestDTO categoryRequestDTO,
                             @MappingTarget CategoryEntity categoryEntity);
}
