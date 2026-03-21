package org.example.electronics.mapper;

import org.example.electronics.dto.request.admin.AdminProductRequestDTO;
import org.example.electronics.dto.response.admin.product.AdminDetailProductResponseDTO;
import org.example.electronics.dto.response.admin.product.AdminProductResponseDTO;
import org.example.electronics.entity.ProductEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        uses = {VariantMapper.class, CategoryMapper.class, BrandMapper.class, MediaMapper.class}
)
public interface ProductMapper {

    @Mapping(target = "category", ignore = true)
    @Mapping(target = "brand", ignore = true)
    @Mapping(target = "media", ignore = true)
    ProductEntity toEntity(AdminProductRequestDTO adminProductRequestDTO);

    @Mapping(source = "category.id", target = "categoryId")
    @Mapping(source = "category.name", target = "categoryName")
    @Mapping(source = "brand.id", target = "brandId")
    @Mapping(source = "brand.name", target = "brandName")
    AdminProductResponseDTO toResponseDTO(ProductEntity productEntity);

    @Mapping(source = "category.id", target = "categoryId")
    @Mapping(source = "category.name", target = "categoryName")
    @Mapping(source = "brand.id", target = "brandId")
    @Mapping(source = "brand.name", target = "brandName")
    AdminDetailProductResponseDTO toDetailResponseDTO(ProductEntity productEntity);

    @Mapping(target = "category", ignore = true)
    @Mapping(target = "brand", ignore = true)
    @Mapping(target = "media", ignore = true)
    void updateEntityFromDTO(AdminProductRequestDTO adminProductRequestDTO,
                             @MappingTarget ProductEntity productEntity);
}
