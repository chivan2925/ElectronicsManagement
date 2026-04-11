package org.example.electronics.service.admin;

import org.example.electronics.dto.response.admin.AdminPaymentTransactionResponseDTO;
import org.example.electronics.entity.enums.PaymentTransactionStatus;
import org.example.electronics.entity.enums.PaymentTransactionType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;

public interface AdminPaymentTransactionService {

    Page<AdminPaymentTransactionResponseDTO> getAllPaymentTransactions(String keyword, PaymentTransactionType type, PaymentTransactionStatus status, LocalDateTime fromDate, LocalDateTime toDate, Pageable pageable);

    AdminPaymentTransactionResponseDTO getPaymentTransactionById(Integer id);

    // Hàm này sẽ được gọi từ AdminReturnRequestService khi sếp đổi status thành COMPLETED
    void processRefund(Integer returnRequestId, Integer staffId);
}
