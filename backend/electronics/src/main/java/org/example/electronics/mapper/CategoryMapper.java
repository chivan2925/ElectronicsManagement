package org.example.electronics.mapper;

import org.example.electronics.dto.request.CategoryRequestDTO;
import org.example.electronics.dto.response.CategoryResponseDTO;
import org.example.electronics.entity.CategoryEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    @Mapping(source = "parentId", target = "parent.id")
    CategoryEntity toEntity(CategoryRequestDTO categoryRequestDTO);

    @Mapping(source = "parent.id", target = "parentId")
    CategoryResponseDTO toResponseDTO(CategoryEntity categoryEntity);
}
