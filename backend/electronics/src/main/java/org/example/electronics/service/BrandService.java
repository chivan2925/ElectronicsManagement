package org.example.electronics.service;

import org.example.electronics.dto.request.BrandRequestDTO;
import org.example.electronics.dto.response.BrandResponseDTO;
import org.example.electronics.entity.BrandEntity;
import org.example.electronics.repository.BrandRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BrandService {

    private final BrandRepository brandRepository;

    public BrandService(BrandRepository brandRepository) {
        this.brandRepository = brandRepository;
    }

    // ========== Public ==========

    public List<BrandResponseDTO> getAll() {
        return brandRepository.findAll().stream().map(this::toResponseDTO).toList();
    }

    // ========== Admin ==========

    public BrandResponseDTO getById(Integer id) {
        return toResponseDTO(findById(id));
    }

    public BrandResponseDTO create(BrandRequestDTO request) {
        if (brandRepository.existsByName(request.name())) {
            throw new RuntimeException("Tên thương hiệu đã tồn tại");
        }
        BrandEntity brand = BrandEntity.builder()
                .name(request.name())
                .imageUrl(request.imageUrl())
                .build();
        brandRepository.save(brand);
        return toResponseDTO(brand);
    }

    public BrandResponseDTO update(Integer id, BrandRequestDTO request) {
        BrandEntity brand = findById(id);
        brand.setName(request.name());
        brand.setImageUrl(request.imageUrl());
        brandRepository.save(brand);
        return toResponseDTO(brand);
    }

    public void delete(Integer id) {
        BrandEntity brand = findById(id);
        brandRepository.delete(brand);
    }

    private BrandEntity findById(Integer id) {
        return brandRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy thương hiệu"));
    }

    private BrandResponseDTO toResponseDTO(BrandEntity b) {
        return new BrandResponseDTO(
                b.getId(), b.getName(), b.getImageUrl(),
                b.getStatus(), b.getCreatedAt(), b.getUpdatedAt()
        );
    }
}
