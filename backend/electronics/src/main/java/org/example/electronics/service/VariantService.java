package org.example.electronics.service;

import org.example.electronics.dto.request.VariantRequestDTO;
import org.example.electronics.dto.response.VariantResponseDTO;
import org.example.electronics.entity.ProductEntity;
import org.example.electronics.entity.VariantEntity;
import org.example.electronics.repository.ProductRepository;
import org.example.electronics.repository.VariantRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VariantService {

    private final VariantRepository variantRepository;
    private final ProductRepository productRepository;

    public VariantService(VariantRepository variantRepository, ProductRepository productRepository) {
        this.variantRepository = variantRepository;
        this.productRepository = productRepository;
    }

    public VariantResponseDTO create(Integer productId, VariantRequestDTO request) {
        ProductEntity product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy sản phẩm"));

        VariantEntity variant = VariantEntity.builder()
                .product(product)
                .name(request.name())
                .color(request.color())
                .price(request.price())
                .stock(request.stock() != null ? request.stock() : 0)
                .build();

        variantRepository.save(variant);
        return toResponseDTO(variant);
    }

    public VariantResponseDTO update(Integer id, VariantRequestDTO request) {
        VariantEntity variant = findById(id);
        variant.setName(request.name());
        variant.setColor(request.color());
        variant.setPrice(request.price());
        if (request.stock() != null) variant.setStock(request.stock());

        variantRepository.save(variant);
        return toResponseDTO(variant);
    }

    public void delete(Integer id) {
        VariantEntity variant = findById(id);
        variantRepository.delete(variant);
    }

    public VariantResponseDTO getById(Integer id) {
        return toResponseDTO(findById(id));
    }

    public List<VariantResponseDTO> getByProductId(Integer productId) {
        return variantRepository.findByProductId(productId).stream()
                .map(this::toResponseDTO).toList();
    }

    private VariantEntity findById(Integer id) {
        return variantRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy biến thể"));
    }

    private VariantResponseDTO toResponseDTO(VariantEntity v) {
        return new VariantResponseDTO(
                v.getId(), v.getProduct().getId(),
                v.getName(), v.getColor(), v.getPrice(),
                v.getStock(), v.getStatus(), v.getCreatedAt()
        );
    }
}
