package org.example.electronics.service.admin;

import org.example.electronics.dto.request.admin.status.AdminUpdateWarehouseTransactionTypeStatusRequestDTO;
import org.example.electronics.dto.request.admin.warehouse.transaction.AdminWarehouseTransactionRequestDTO;
import org.example.electronics.dto.response.admin.warehouse.transaction.AdminWarehouseTransactionResponseDTO;
import org.example.electronics.entity.enums.WarehouseTransactionStatus;
import org.example.electronics.entity.enums.WarehouseTransactionType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;

public interface AdminWarehouseTransactionService {

    AdminWarehouseTransactionResponseDTO createWarehouseTransaction(AdminWarehouseTransactionRequestDTO adminWarehouseTransactionRequestDTO, Integer staffId);
    AdminWarehouseTransactionResponseDTO updateWarehouseTransaction(Integer warehouseTransactionId, AdminWarehouseTransactionRequestDTO adminWarehouseTransactionRequestDTO, Integer staffId);
    AdminWarehouseTransactionResponseDTO updateStatusWarehouseTransaction(Integer warehouseTransactionId, AdminUpdateWarehouseTransactionTypeStatusRequestDTO adminUpdateWarehouseTransactionTypeStatusRequestDTO, Integer staffId);
    void deleteWarehouseTransaction(Integer warehouseTransactionId, Integer staffId);
    Page<AdminWarehouseTransactionResponseDTO> getAllWarehouseTransactions(String keyword, WarehouseTransactionType type, WarehouseTransactionStatus status, LocalDate fromDate, LocalDate toDate, Pageable pageable);
    AdminWarehouseTransactionResponseDTO getWarehouseTransactionById(Integer warehouseTransactionId);
}
