package org.example.electronics.controller;

import jakarta.validation.Valid;
import org.example.electronics.dto.request.CreatePaymentRequestDTO;
import org.example.electronics.dto.response.ApiResponse;
import org.example.electronics.dto.response.PaymentCreateResponseDTO;
import org.example.electronics.dto.response.PaymentTransactionResponseDTO;
import org.example.electronics.security.RequirePermission;
import org.example.electronics.service.PaymentService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    // ==================== Customer ====================

    @PostMapping("/api/payments/create")
    public ResponseEntity<ApiResponse<PaymentCreateResponseDTO>> createPayment(
            @AuthenticationPrincipal UserDetails userDetails,
            @Valid @RequestBody CreatePaymentRequestDTO request) {
        PaymentCreateResponseDTO response = paymentService.createPayment(
                userDetails.getUsername(), request);
        return ResponseEntity.ok(ApiResponse.success("Tạo thanh toán thành công", response));
    }

    // ==================== Webhook (public, called by provider) ====================

    @PostMapping("/api/payments/webhook")
    public ResponseEntity<ApiResponse<PaymentTransactionResponseDTO>> webhook(
            @RequestBody Map<String, String> payload) {
        PaymentTransactionResponseDTO response = paymentService.handleWebhook(payload);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    // ==================== Admin ====================

    @GetMapping("/api/admin/orders/{orderId}/payments")
    @RequirePermission("MANAGE_ORDER")
    public ResponseEntity<ApiResponse<List<PaymentTransactionResponseDTO>>> getByOrderId(
            @PathVariable Integer orderId) {
        return ResponseEntity.ok(ApiResponse.success(paymentService.getByOrderId(orderId)));
    }
}
