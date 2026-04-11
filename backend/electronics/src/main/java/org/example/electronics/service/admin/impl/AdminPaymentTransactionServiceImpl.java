package org.example.electronics.service.admin.impl;

import lombok.RequiredArgsConstructor;
import org.example.electronics.dto.response.admin.AdminPaymentTransactionResponseDTO;
import org.example.electronics.entity.enums.PaymentTransactionStatus;
import org.example.electronics.entity.enums.PaymentTransactionType;
import org.example.electronics.mapper.PaymentTransactionMapper;
import org.example.electronics.repository.PaymentTransactionRepository;
import org.example.electronics.service.admin.AdminPaymentTransactionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AdminPaymentTransactionServiceImpl implements AdminPaymentTransactionService {

    private final PaymentTransactionMapper paymentTransactionMapper;
    private final PaymentTransactionRepository paymentTransactionRepository;

    @Transactional(readOnly = true)
    @Override
    public Page<AdminPaymentTransactionResponseDTO> getAllPaymentTransactions(String keyword, PaymentTransactionType type, PaymentTransactionStatus status, LocalDateTime fromDate, LocalDateTime toDate, Pageable pageable) {
        return null;
    }

    @Transactional(readOnly = true)
    @Override
    public AdminPaymentTransactionResponseDTO getPaymentTransactionById(Integer id) {
        return null;
    }

    @Transactional
    @Override
    public void processRefund(Integer returnRequestId, Integer staffId) {

    }
}
