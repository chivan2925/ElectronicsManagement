package org.example.electronics.service;

import org.example.electronics.dto.request.ProductRequestDTO;
import org.example.electronics.dto.response.*;
import org.example.electronics.entity.BrandEntity;
import org.example.electronics.entity.CategoryEntity;
import org.example.electronics.entity.MediaEntity;
import org.example.electronics.entity.ProductEntity;
import org.example.electronics.entity.enums.ProductStatus;
import org.example.electronics.repository.BrandRepository;
import org.example.electronics.repository.CategoryRepository;
import org.example.electronics.repository.ProductRepository;
import org.example.electronics.specification.ProductSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final BrandRepository brandRepository;

    public ProductService(ProductRepository productRepository,
                          CategoryRepository categoryRepository,
                          BrandRepository brandRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.brandRepository = brandRepository;
    }

    // ========== Admin ==========

    public ProductResponseDTO create(ProductRequestDTO request) {
        if (productRepository.existsBySlug(request.slug())) {
            throw new RuntimeException("Slug đã tồn tại");
        }

        ProductEntity product = ProductEntity.builder()
                .name(request.name())
                .slug(request.slug())
                .description(request.description())
                .build();

        if (request.categoryId() != null) {
            CategoryEntity category = categoryRepository.findById(request.categoryId())
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy danh mục"));
            product.setCategory(category);
        }
        if (request.brandId() != null) {
            BrandEntity brand = brandRepository.findById(request.brandId())
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy thương hiệu"));
            product.setBrand(brand);
        }

        productRepository.save(product);
        return toResponseDTO(product);
    }

    public ProductResponseDTO update(Integer id, ProductRequestDTO request) {
        ProductEntity product = findById(id);
        product.setName(request.name());
        product.setSlug(request.slug());
        product.setDescription(request.description());

        if (request.categoryId() != null) {
            CategoryEntity category = categoryRepository.findById(request.categoryId())
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy danh mục"));
            product.setCategory(category);
        }
        if (request.brandId() != null) {
            BrandEntity brand = brandRepository.findById(request.brandId())
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy thương hiệu"));
            product.setBrand(brand);
        }

        productRepository.save(product);
        return toResponseDTO(product);
    }

    public void delete(Integer id) {
        ProductEntity product = findById(id);
        product.setStatus(ProductStatus.DELETED);
        productRepository.save(product);
    }

    // ========== Public ==========

    public Page<ProductResponseDTO> search(Integer categoryId, Integer brandId,
                                            String keyword, BigDecimal minPrice, BigDecimal maxPrice,
                                            String sortBy, String sortDir,
                                            int page, int size) {
        Specification<ProductEntity> spec = Specification
                .where(ProductSpecification.hasStatus(ProductStatus.ACTIVE))
                .and(ProductSpecification.hasCategory(categoryId))
                .and(ProductSpecification.hasBrand(brandId))
                .and(ProductSpecification.hasKeyword(keyword))
                .and(ProductSpecification.hasPriceRange(minPrice, maxPrice));

        Sort sort = Sort.by(Sort.Direction.fromString(sortDir != null ? sortDir : "desc"),
                sortBy != null ? sortBy : "createdAt");

        Pageable pageable = PageRequest.of(page, size, sort);
        return productRepository.findAll(spec, pageable).map(this::toResponseDTO);
    }

    public ProductDetailResponseDTO getBySlug(String slug) {
        ProductEntity product = productRepository.findBySlugWithDetails(slug)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy sản phẩm"));
        return toDetailResponseDTO(product);
    }

    // ========== Helpers ==========

    private ProductEntity findById(Integer id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy sản phẩm"));
    }

    private ProductResponseDTO toResponseDTO(ProductEntity p) {
        String primaryImage = p.getMedia() != null
                ? p.getMedia().stream()
                    .filter(MediaEntity::getIsPrimary)
                    .findFirst()
                    .map(MediaEntity::getImageUrl)
                    .orElse(null)
                : null;

        return new ProductResponseDTO(
                p.getId(), p.getName(), p.getSlug(),
                p.getCategory() != null ? p.getCategory().getId() : null,
                p.getCategory() != null ? p.getCategory().getName() : null,
                p.getBrand() != null ? p.getBrand().getId() : null,
                p.getBrand() != null ? p.getBrand().getName() : null,
                p.getDescription(),
                p.getRatingStar(), p.getRatingCount(),
                p.getStatus(), primaryImage,
                p.getCreatedAt(), p.getUpdatedAt()
        );
    }

    private ProductDetailResponseDTO toDetailResponseDTO(ProductEntity p) {
        List<VariantResponseDTO> variants = p.getVariants() != null
                ? p.getVariants().stream().map(v -> new VariantResponseDTO(
                    v.getId(), v.getProduct().getId(),
                    v.getName(), v.getColor(), v.getPrice(),
                    v.getStock(), v.getStatus(), v.getCreatedAt()
                )).toList()
                : List.of();

        List<MediaResponseDTO> media = p.getMedia() != null
                ? p.getMedia().stream().map(m -> new MediaResponseDTO(
                    m.getId(), m.getProduct().getId(),
                    m.getImageUrl(), m.getIsPrimary(),
                    m.getSortOrder(), m.getCreatedAt()
                )).toList()
                : List.of();

        return new ProductDetailResponseDTO(
                p.getId(), p.getName(), p.getSlug(),
                p.getCategory() != null ? p.getCategory().getId() : null,
                p.getCategory() != null ? p.getCategory().getName() : null,
                p.getBrand() != null ? p.getBrand().getId() : null,
                p.getBrand() != null ? p.getBrand().getName() : null,
                p.getDescription(),
                p.getRatingStar(), p.getRatingCount(),
                p.getStatus(), variants, media,
                p.getCreatedAt(), p.getUpdatedAt()
        );
    }
}
