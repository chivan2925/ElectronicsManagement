package org.example.electronics.service;

import org.example.electronics.dto.request.WarehouseTransactionRequestDTO;
import org.example.electronics.dto.response.WarehouseTransactionItemResponseDTO;
import org.example.electronics.dto.response.WarehouseTransactionResponseDTO;
import org.example.electronics.entity.*;
import org.example.electronics.entity.enums.WarehouseTransactionStatus;
import org.example.electronics.entity.enums.WarehouseTransactionType;
import org.example.electronics.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class WarehouseTransactionService {

    private final WarehouseTransactionRepository transactionRepository;
    private final WarehouseRepository warehouseRepository;
    private final WarehouseDetailRepository detailRepository;
    private final VariantRepository variantRepository;

    public WarehouseTransactionService(WarehouseTransactionRepository transactionRepository,
                                       WarehouseRepository warehouseRepository,
                                       WarehouseDetailRepository detailRepository,
                                       VariantRepository variantRepository) {
        this.transactionRepository = transactionRepository;
        this.warehouseRepository = warehouseRepository;
        this.detailRepository = detailRepository;
        this.variantRepository = variantRepository;
    }

    // ========== Create Transaction (PENDING) ==========

    @Transactional
    public WarehouseTransactionResponseDTO create(WarehouseTransactionRequestDTO request) {
        WarehouseEntity warehouse = warehouseRepository.findById(request.warehouseId())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy kho"));

        WarehouseTransactionEntity transaction = WarehouseTransactionEntity.builder()
                .warehouse(warehouse)
                .type(request.type())
                .note(request.note())
                .orderId(request.orderId())
                .returnRequestId(request.returnRequestId())
                .date(LocalDateTime.now())
                .build();

        transactionRepository.save(transaction);

        // Add detail items
        for (var item : request.items()) {
            VariantEntity variant = variantRepository.findById(item.variantId())
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy biến thể ID " + item.variantId()));

            WarehouseTransactionDetailEntity detail = WarehouseTransactionDetailEntity.builder()
                    .transaction(transaction)
                    .variant(variant)
                    .quantity(item.quantity())
                    .build();
            transaction.getDetails().add(detail);
        }

        transactionRepository.save(transaction);
        return toResponseDTO(transaction);
    }

    // ========== Approve Transaction ==========

    @Transactional
    public WarehouseTransactionResponseDTO approve(Integer id) {
        WarehouseTransactionEntity transaction = transactionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy phiếu"));

        if (transaction.getStatus() != WarehouseTransactionStatus.PENDING) {
            throw new RuntimeException("Phiếu đã được duyệt hoặc từ chối");
        }

        WarehouseEntity warehouse = transaction.getWarehouse();

        for (WarehouseTransactionDetailEntity detail : transaction.getDetails()) {
            VariantEntity variant = detail.getVariant();
            int qty = detail.getQuantity();

            // Update warehouse_details
            WarehouseDetailEntity warehouseDetail = detailRepository
                    .findByWarehouseIdAndVariantId(warehouse.getId(), variant.getId())
                    .orElseGet(() -> WarehouseDetailEntity.builder()
                            .warehouse(warehouse)
                            .variant(variant)
                            .quantity(0)
                            .build());

            if (transaction.getType() == WarehouseTransactionType.IN) {
                warehouseDetail.setQuantity(warehouseDetail.getQuantity() + qty);
                warehouse.setCurrentStock(warehouse.getCurrentStock() + qty);
            } else { // OUT
                if (warehouseDetail.getQuantity() < qty) {
                    throw new RuntimeException("Tồn kho biến thể '" + variant.getName()
                            + "' trong kho không đủ (còn " + warehouseDetail.getQuantity() + ")");
                }
                warehouseDetail.setQuantity(warehouseDetail.getQuantity() - qty);
                warehouse.setCurrentStock(warehouse.getCurrentStock() - qty);
            }

            detailRepository.save(warehouseDetail);
        }

        warehouseRepository.save(warehouse);
        transaction.setStatus(WarehouseTransactionStatus.APPROVED);
        transactionRepository.save(transaction);

        return toResponseDTO(transaction);
    }

    // ========== Reject ==========

    public WarehouseTransactionResponseDTO reject(Integer id) {
        WarehouseTransactionEntity transaction = transactionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy phiếu"));
        if (transaction.getStatus() != WarehouseTransactionStatus.PENDING) {
            throw new RuntimeException("Phiếu đã được duyệt hoặc từ chối");
        }
        transaction.setStatus(WarehouseTransactionStatus.REJECTED);
        transactionRepository.save(transaction);
        return toResponseDTO(transaction);
    }

    // ========== Query ==========

    public List<WarehouseTransactionResponseDTO> getAll() {
        return transactionRepository.findAll().stream().map(this::toResponseDTO).toList();
    }

    public WarehouseTransactionResponseDTO getById(Integer id) {
        return toResponseDTO(transactionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy phiếu")));
    }

    public List<WarehouseTransactionResponseDTO> getByWarehouseId(Integer warehouseId) {
        return transactionRepository.findByWarehouseIdOrderByCreatedAtDesc(warehouseId)
                .stream().map(this::toResponseDTO).toList();
    }

    // ========== Order Linking ==========

    @Transactional
    public WarehouseTransactionResponseDTO createFromOrder(Integer warehouseId, OrderEntity order) {
        WarehouseEntity warehouse = warehouseRepository.findById(warehouseId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy kho"));

        WarehouseTransactionEntity transaction = WarehouseTransactionEntity.builder()
                .warehouse(warehouse)
                .type(WarehouseTransactionType.OUT)
                .orderId(order.getId())
                .note("Xuất kho cho đơn hàng " + order.getCode())
                .date(LocalDateTime.now())
                .status(WarehouseTransactionStatus.APPROVED) // Auto-approve for orders
                .build();

        transactionRepository.save(transaction);

        for (OrderDetailEntity od : order.getOrderDetails()) {
            WarehouseTransactionDetailEntity detail = WarehouseTransactionDetailEntity.builder()
                    .transaction(transaction)
                    .variant(od.getVariant())
                    .quantity(od.getQuantity())
                    .build();
            transaction.getDetails().add(detail);

            // Update warehouse detail
            WarehouseDetailEntity whDetail = detailRepository
                    .findByWarehouseIdAndVariantId(warehouse.getId(), od.getVariant().getId())
                    .orElse(null);
            if (whDetail != null) {
                whDetail.setQuantity(whDetail.getQuantity() - od.getQuantity());
                detailRepository.save(whDetail);
            }
        }

        // Update warehouse total
        int totalQty = order.getOrderDetails().stream().mapToInt(OrderDetailEntity::getQuantity).sum();
        warehouse.setCurrentStock(warehouse.getCurrentStock() - totalQty);
        warehouseRepository.save(warehouse);

        transactionRepository.save(transaction);
        return toResponseDTO(transaction);
    }

    // ========== Helpers ==========

    private WarehouseTransactionResponseDTO toResponseDTO(WarehouseTransactionEntity t) {
        List<WarehouseTransactionItemResponseDTO> items = t.getDetails() != null
                ? t.getDetails().stream().map(d -> new WarehouseTransactionItemResponseDTO(
                    d.getVariant().getId(),
                    d.getVariant().getName(),
                    d.getVariant().getProduct().getName(),
                    d.getQuantity()
                )).toList()
                : List.of();

        return new WarehouseTransactionResponseDTO(
                t.getId(), t.getWarehouse().getId(), t.getWarehouse().getName(),
                t.getType(), t.getStatus(), t.getNote(),
                t.getOrderId(), t.getReturnRequestId(),
                items, t.getCreatedAt()
        );
    }
}
