package org.example.electronics.mapper;

import org.example.electronics.dto.request.admin.AdminCouponRequestDTO;
import org.example.electronics.dto.response.admin.AdminCouponResponseDTO;
import org.example.electronics.entity.CouponEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface CouponMapper {

    @Mapping(target = "category", ignore = true)
    @Mapping(target = "brand", ignore = true)
    CouponEntity toEntity(AdminCouponRequestDTO adminCouponRequestDTO);

    @Mapping(source = "category.id", target = "categoryId")
    @Mapping(source = "brand.id", target = "brandId")
    AdminCouponResponseDTO toResponseDTO(CouponEntity couponEntity);

    @Mapping(target = "category", ignore = true)
    @Mapping(target = "brand", ignore = true)
    void updateEntityFromDTO(AdminCouponRequestDTO adminCouponRequestDTO,
                             @MappingTarget CouponEntity couponEntity);
}
