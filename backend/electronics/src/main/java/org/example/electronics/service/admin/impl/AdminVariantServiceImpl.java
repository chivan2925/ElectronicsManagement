package org.example.electronics.service.admin.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.example.electronics.dto.request.admin.status.AdminUpdateProductStatusRequestDTO;
import org.example.electronics.dto.request.admin.AdminVariantRequestDTO;
import org.example.electronics.dto.request.admin.media.AdminNestedMediaRequestDTO;
import org.example.electronics.dto.response.admin.variant.AdminDetailVariantResponseDTO;
import org.example.electronics.dto.response.admin.variant.AdminVariantResponseDTO;
import org.example.electronics.dto.response.admin.variant.AdminVariantWarehouseStockResponseDTO;
import org.example.electronics.entity.MediaEntity;
import org.example.electronics.entity.ProductEntity;
import org.example.electronics.entity.VariantEntity;
import org.example.electronics.entity.enums.ProductStatus;
import org.example.electronics.entity.warehouse.WarehouseDetailEntity;
import org.example.electronics.mapper.MediaMapper;
import org.example.electronics.mapper.VariantMapper;
import org.example.electronics.repository.MediaRepository;
import org.example.electronics.repository.ProductRepository;
import org.example.electronics.repository.VariantRepository;
import org.example.electronics.service.admin.AdminVariantService;
import org.example.electronics.util.DateTimeUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminVariantServiceImpl implements AdminVariantService {

    private final VariantMapper variantMapper;
    private final VariantRepository variantRepository;
    private final ProductRepository productRepository;
    private final MediaMapper mediaMapper;
    private final MediaRepository mediaRepository;

    @Transactional
    @Override
    public AdminVariantResponseDTO createVariant(AdminVariantRequestDTO adminVariantRequestDTO) {
        if(variantRepository.existsByName(adminVariantRequestDTO.name()) ||
                variantRepository.existsBySlug(adminVariantRequestDTO.slug())) {
            throw new IllegalArgumentException("Tên hoặc Slug biến thể này đã tồn tại.");
        }

        ProductEntity existingProductEntity = productRepository.findById(adminVariantRequestDTO.productId())
                .orElseThrow(() -> new EntityNotFoundException(
                        "Không tìm thấy sản phẩm với id: " + adminVariantRequestDTO.productId()
                ));

        VariantEntity newVariantEntity = variantMapper.toNewEntity(adminVariantRequestDTO);

        newVariantEntity.setProduct(existingProductEntity);

        VariantEntity savedVariantEntity = variantRepository.save(newVariantEntity);

        List<AdminNestedMediaRequestDTO> adminNestedMediaRequestDTOList = adminVariantRequestDTO.media();

        if(adminNestedMediaRequestDTOList != null && !adminNestedMediaRequestDTOList.isEmpty()) {
            List<MediaEntity> mediaEntityList = adminNestedMediaRequestDTOList.stream()
                    .map(mediaDTO -> {
                        MediaEntity mediaEntity = mediaMapper.nestedDTO_toNewEntity(mediaDTO);
                        mediaEntity.setVariant(savedVariantEntity);
                        return mediaEntity;
                    })
                    .toList();

            mediaRepository.saveAll(mediaEntityList);
        }

        return variantMapper.toAdminResponseDTO(savedVariantEntity);
    }

    @Transactional
    @Override
    public AdminVariantResponseDTO updateVariant(Integer variantId, AdminVariantRequestDTO adminVariantRequestDTO) {
        if(variantRepository.existsByNameAndIdNot(adminVariantRequestDTO.name(), variantId) ||
                variantRepository.existsBySlugAndIdNot(adminVariantRequestDTO.slug(), variantId)) {
            throw new IllegalArgumentException("Tên hoặc Slug biến thể này đã bị trùng với một biến thể khác.");
        }

        VariantEntity existingVariantEntity = variantRepository.findById(variantId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Không tìm thấy biến thể với id: " + variantId
                ));

        ProductEntity existingProductEntity = productRepository.findById(adminVariantRequestDTO.productId())
                .orElseThrow(() -> new EntityNotFoundException(
                        "Không tìm thấy sản phẩm với id: " + adminVariantRequestDTO.productId()
                ));

        variantMapper.updateEntityFromRequest(adminVariantRequestDTO, existingVariantEntity);

        existingVariantEntity.setProduct(existingProductEntity);

        return variantMapper.toAdminResponseDTO(existingVariantEntity);
    }

    @Transactional
    @Override
    public AdminVariantResponseDTO updateStatusVariant(Integer variantId, AdminUpdateProductStatusRequestDTO adminUpdateProductStatusRequestDTO) {
        VariantEntity existingVariantEntity = variantRepository.findById(variantId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Không tìm thấy biến thể với id: " + variantId
                ));

        existingVariantEntity.setStatus(adminUpdateProductStatusRequestDTO.status());

        return variantMapper.toAdminResponseDTO(existingVariantEntity);
    }

    @Transactional
    @Override
    public void deleteVariant(Integer variantId) {
        VariantEntity existingVariantEntity = variantRepository.findById(variantId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Không tìm thấy biến thể với id: " + variantId
                ));

        existingVariantEntity.setStatus(ProductStatus.DELETED);
    }

    @Transactional(readOnly = true)
    @Override
    public Page<AdminVariantResponseDTO> getAllVariants(String keyword, ProductStatus status, LocalDate fromDate, LocalDate toDate, Pageable pageable) {
        LocalDateTime startDateTime = DateTimeUtils.getStartOfDay(fromDate);
        LocalDateTime endDateTime = DateTimeUtils.getEndOfDay(toDate);

        String finalKeyword = StringUtils.hasText(keyword) ? keyword.trim() : null;

        Page<VariantEntity> variantEntityPage = variantRepository.findVariantsWithFilter(finalKeyword, status, startDateTime, endDateTime, pageable);

        return variantEntityPage.map(variantMapper::toAdminResponseDTO);
    }

    @Transactional(readOnly = true)
    @Override
    public AdminDetailVariantResponseDTO getVariantById(Integer variantId) {
        VariantEntity existingVariantEntity = variantRepository.findVariantWithBasicDetailsById(variantId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Không tìm thấy biến thể với id: " + variantId
                ));

        List<WarehouseDetailEntity> warehouseDetailEntityList = variantRepository.findWarehouseStocksByVariantId(variantId);

        List<AdminVariantWarehouseStockResponseDTO> warehouseStockResponseDTOList = warehouseDetailEntityList.stream()
                .map(variantMapper::toWarehouseStockDTO)
                .toList();

        int totalStock = warehouseDetailEntityList.stream()
                .mapToInt(WarehouseDetailEntity::getQuantity)
                .sum();

        BigDecimal totalWarehouseValue = existingVariantEntity.getPrice()
                .multiply(BigDecimal.valueOf(totalStock));

        return variantMapper.toAdminDetailResponseDTO(
                existingVariantEntity,
                warehouseStockResponseDTOList,
                totalStock,
                totalWarehouseValue
        );
    }
}
