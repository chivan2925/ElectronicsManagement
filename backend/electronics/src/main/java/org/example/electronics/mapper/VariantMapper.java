package org.example.electronics.mapper;

import org.example.electronics.dto.request.admin.AdminVariantRequestDTO;
import org.example.electronics.dto.response.admin.AdminVariantResponseDTO;
import org.example.electronics.entity.MediaEntity;
import org.example.electronics.entity.VariantEntity;
import org.mapstruct.*;

import java.util.List;

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
    @Mapping(source = "media", target = "primaryImageUrl", qualifiedByName = "getPrimaryImage")
    AdminVariantResponseDTO toResponseDTO(VariantEntity variantEntity);

    @Mapping(target = "product", ignore = true)
    @Mapping(target = "media", ignore = true)
    void updateEntityFromDTO(AdminVariantRequestDTO adminVariantRequestDTO,
                             @MappingTarget VariantEntity VariantEntity);

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
