package org.example.electronics.service;

import org.example.electronics.dto.request.WarehouseRequestDTO;
import org.example.electronics.dto.response.WarehouseDetailResponseDTO;
import org.example.electronics.dto.response.WarehouseResponseDTO;
import org.example.electronics.entity.WarehouseDetailEntity;
import org.example.electronics.entity.WarehouseEntity;
import org.example.electronics.entity.VariantEntity;
import org.example.electronics.repository.VariantRepository;
import org.example.electronics.repository.WarehouseDetailRepository;
import org.example.electronics.repository.WarehouseRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WarehouseService {

    private final WarehouseRepository warehouseRepository;
    private final WarehouseDetailRepository detailRepository;
    private final VariantRepository variantRepository;

    public WarehouseService(WarehouseRepository warehouseRepository,
                            WarehouseDetailRepository detailRepository,
                            VariantRepository variantRepository) {
        this.warehouseRepository = warehouseRepository;
        this.detailRepository = detailRepository;
        this.variantRepository = variantRepository;
    }

    // ========== CRUD ==========

    public List<WarehouseResponseDTO> getAll() {
        return warehouseRepository.findAll().stream().map(this::toResponseDTO).toList();
    }

    public WarehouseResponseDTO getById(Integer id) {
        return toResponseDTO(findById(id));
    }

    public WarehouseResponseDTO create(WarehouseRequestDTO request) {
        WarehouseEntity warehouse = WarehouseEntity.builder()
                .name(request.name())
                .line(request.line())
                .ward(request.ward())
                .district(request.district())
                .province(request.province())
                .capacity(request.capacity())
                .build();
        warehouseRepository.save(warehouse);
        return toResponseDTO(warehouse);
    }

    public WarehouseResponseDTO update(Integer id, WarehouseRequestDTO request) {
        WarehouseEntity warehouse = findById(id);
        warehouse.setName(request.name());
        warehouse.setLine(request.line());
        warehouse.setWard(request.ward());
        warehouse.setDistrict(request.district());
        warehouse.setProvince(request.province());
        warehouse.setCapacity(request.capacity());
        warehouseRepository.save(warehouse);
        return toResponseDTO(warehouse);
    }

    public void delete(Integer id) {
        WarehouseEntity warehouse = findById(id);
        warehouseRepository.delete(warehouse);
    }

    // ========== Stock Details ==========

    public List<WarehouseDetailResponseDTO> getStock(Integer warehouseId) {
        return detailRepository.findByWarehouseId(warehouseId).stream()
                .map(this::toDetailDTO).toList();
    }

    // ========== Helpers ==========

    WarehouseEntity findById(Integer id) {
        return warehouseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy kho"));
    }

    private WarehouseResponseDTO toResponseDTO(WarehouseEntity w) {
        return new WarehouseResponseDTO(
                w.getId(), w.getName(), w.getLine(), w.getWard(),
                w.getDistrict(), w.getProvince(), w.getCapacity(),
                w.getCurrentStock(), w.getStatus(),
                w.getCreatedAt(), w.getUpdatedAt()
        );
    }

    private WarehouseDetailResponseDTO toDetailDTO(WarehouseDetailEntity d) {
        return new WarehouseDetailResponseDTO(
                d.getWarehouse().getId(), d.getVariant().getId(),
                d.getVariant().getName(), d.getVariant().getProduct().getName(),
                d.getQuantity()
        );
    }
}
