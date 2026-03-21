package org.example.electronics.mapper;

import org.example.electronics.dto.request.admin.AdminVariantRequestDTO;
import org.example.electronics.dto.response.admin.AdminVariantResponseDTO;
import org.example.electronics.entity.VariantEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        uses = {MediaMapper.class}
)
public interface VariantMapper {

    @Mapping(target = "product", ignore = true)
    @Mapping(target = "media", ignore = true)
    VariantEntity toEntity(AdminVariantRequestDTO adminVariantRequestDTO);

    @Mapping(source = "product.id", target = "productId")
    @Mapping(source = "product.name", target = "productName")
    AdminVariantResponseDTO toResponseDTO(VariantEntity variantEntity);

    @Mapping(target = "product", ignore = true)
    @Mapping(target = "media", ignore = true)
    void updateEntityFromDTO(AdminVariantRequestDTO adminVariantRequestDTO,
                             @MappingTarget VariantEntity VariantEntity);
}
