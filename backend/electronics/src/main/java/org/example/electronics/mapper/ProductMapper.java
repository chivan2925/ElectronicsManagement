package org.example.electronics.mapper;

import org.example.electronics.dto.request.admin.AdminProductRequestDTO;
import org.example.electronics.dto.response.admin.product.AdminDetailProductResponseDTO;
import org.example.electronics.dto.response.admin.product.AdminProductResponseDTO;
import org.example.electronics.entity.MediaEntity;
import org.example.electronics.entity.ProductEntity;
import org.mapstruct.*;

import java.util.List;

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
    @Mapping(source = "media", target = "primaryImageUrl", qualifiedByName = "getPrimaryImage")
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

    @SuppressWarnings("unused")
    @Named("getPrimaryImage")
    default String getPrimaryImage(List<MediaEntity> mediaEntityList) {
        if (mediaEntityList == null || mediaEntityList.isEmpty()) {
            return null;
        }

        return mediaEntityList.stream()
                .filter(media -> Boolean.TRUE.equals(media.getIsPrimary()))
                .map(MediaEntity::getImageUrl)
                .findFirst()
                .orElse(mediaEntityList.getFirst().getImageUrl());
    }
}
