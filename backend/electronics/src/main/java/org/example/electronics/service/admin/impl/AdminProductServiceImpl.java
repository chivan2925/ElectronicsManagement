package org.example.electronics.service.admin.impl;

import jakarta.persistence.EntityNotFoundException;
import org.example.electronics.dto.request.admin.AdminProductRequestDTO;
import org.example.electronics.dto.request.admin.AdminUpdateProductStatusRequestDTO;
import org.example.electronics.dto.response.admin.product.AdminDetailProductResponseDTO;
import org.example.electronics.dto.response.admin.product.AdminProductResponseDTO;
import org.example.electronics.entity.BrandEntity;
import org.example.electronics.entity.CategoryEntity;
import org.example.electronics.entity.ProductEntity;
import org.example.electronics.entity.enums.ProductStatus;
import org.example.electronics.mapper.ProductMapper;
import org.example.electronics.repository.CategoryRepository;
import org.example.electronics.repository.ProductRepository;
import org.example.electronics.repository.VariantRepository;
import org.example.electronics.service.admin.AdminProductService;
import org.example.electronics.util.DateTimeUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
public class AdminProductServiceImpl implements AdminProductService {

    private final ProductMapper productMapper;
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final BrandRepository brandRepository;
    private final VariantRepository variantRepository;

    public AdminProductServiceImpl(ProductMapper productMapper, ProductRepository productRepository, CategoryRepository categoryRepository, BrandRepository brandRepository, VariantRepository variantRepository) {
        this.productMapper = productMapper;
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.brandRepository = brandRepository;
        this.variantRepository = variantRepository;
    }

    @Transactional
    @Override
    public AdminProductResponseDTO createProduct(AdminProductRequestDTO adminProductRequestDTO) {
        if(productRepository.existsByName(adminProductRequestDTO.name()) ||
           productRepository.existsBySlug(adminProductRequestDTO.slug())) {
            throw new IllegalArgumentException("Tên hoặc Slug sản phẩm này đã tồn tại.");
        }

        CategoryEntity existingCategoryEntity = categoryRepository.findById(adminProductRequestDTO.categoryId())
                .orElseThrow(() -> new EntityNotFoundException(
                        "Không tìm thấy danh mục với id: " + adminProductRequestDTO.categoryId()
                ));

        BrandEntity existingBrandEntity = brandRepository.findById(adminProductRequestDTO.brandId())
                .orElseThrow(() -> new EntityNotFoundException(
                        "Không tìm thấy thương hiệu với id: " + adminProductRequestDTO.brandId()
                ));

        ProductEntity newProductEntity = productMapper.toEntity(adminProductRequestDTO);

        newProductEntity.setCategory(existingCategoryEntity);
        newProductEntity.setBrand(existingBrandEntity);

        newProductEntity = productRepository.save(newProductEntity);

        return productMapper.toResponseDTO(newProductEntity);
    }

    @Transactional
    @Override
    public AdminProductResponseDTO updateProduct(Integer productId, AdminProductRequestDTO adminProductRequestDTO) {
        if(productRepository.existsByNameAndIdNot(adminProductRequestDTO.name(), productId) ||
                productRepository.existsBySlugAndIdNot(adminProductRequestDTO.slug(), productId)) {
            throw new IllegalArgumentException("Tên hoặc Slug sản phẩm này đã bị trùng với một sản phẩm khác.");
        }

        ProductEntity existingProductEntity = productRepository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Không tìm thấy sản phẩm với id: " + productId
                ));

        CategoryEntity existingCategoryEntity = categoryRepository.findById(adminProductRequestDTO.categoryId())
                .orElseThrow(() -> new EntityNotFoundException(
                        "Không tìm thấy danh mục với id: " + adminProductRequestDTO.categoryId()
                ));

        BrandEntity existingBrandEntity = brandRepository.findById(adminProductRequestDTO.brandId())
                .orElseThrow(() -> new EntityNotFoundException(
                        "Không tìm thấy thương hiệu với id: " + adminProductRequestDTO.brandId()
                ));

        productMapper.updateEntityFromDTO(adminProductRequestDTO, existingProductEntity);

        existingProductEntity.setCategory(existingCategoryEntity);
        existingProductEntity.setBrand(existingBrandEntity);

        existingProductEntity = productRepository.save(existingProductEntity);

        return productMapper.toResponseDTO(existingProductEntity);
    }

    @Transactional
    @Override
    public AdminProductResponseDTO updateStatusProduct(Integer productId, AdminUpdateProductStatusRequestDTO adminUpdateProductStatusRequestDTO) {
        ProductEntity existingProductEntity = productRepository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Không tìm thấy sản phẩm với id: " + productId
                ));

        existingProductEntity.setStatus(adminUpdateProductStatusRequestDTO.status());

        existingProductEntity = productRepository.save(existingProductEntity);

        return productMapper.toResponseDTO(existingProductEntity);
    }

    @Transactional
    @Override
    public void deleteProduct(Integer productId) {
        if (variantRepository.existsByProductId(productId)) {
            throw new IllegalStateException("Không thể xóa sản phẩm này vì nó đang chứa các biến thể.");
        }

        ProductEntity existingProductEntity = productRepository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Không tìm thấy sản phẩm với id: " + productId
                ));

        existingProductEntity.setStatus(ProductStatus.DELETED);

        productRepository.save(existingProductEntity);
    }

    @Transactional(readOnly = true)
    @Override
    public Page<AdminProductResponseDTO> getAllProducts(String keyword, ProductStatus status, LocalDate fromDate, LocalDate toDate, Pageable pageable) {
        LocalDateTime startDateTime = DateTimeUtils.getStartOfDay(fromDate);
        LocalDateTime endDateTime = DateTimeUtils.getEndOfDay(toDate);

        String finalKeyword = StringUtils.hasText(keyword) ? keyword.trim() : null;

        Page<ProductEntity> productEntityPage = productRepository.findProductsWithFilter(finalKeyword, status, startDateTime, endDateTime, pageable);

        return productEntityPage.map(productMapper::toResponseDTO);
    }

    @Transactional(readOnly = true)
    @Override
    public AdminDetailProductResponseDTO getProductById(Integer productId) {
        ProductEntity existingProductEntity = productRepository.findProductWithDetailsById(productId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Không tìm thấy sản phẩm với id: " + productId
                ));

        return productMapper.toDetailResponseDTO(existingProductEntity);
    }
}
