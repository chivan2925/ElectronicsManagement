package org.example.electronics.service;

import org.example.electronics.dto.request.CreatePaymentRequestDTO;
import org.example.electronics.dto.response.PaymentCreateResponseDTO;
import org.example.electronics.dto.response.PaymentTransactionResponseDTO;
import org.example.electronics.entity.OrderEntity;
import org.example.electronics.entity.PaymentTransactionEntity;
import org.example.electronics.entity.enums.PaymentStatus;
import org.example.electronics.entity.enums.PaymentTransactionStatus;
import org.example.electronics.repository.OrderRepository;
import org.example.electronics.repository.PaymentTransactionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class PaymentService {

    private final PaymentTransactionRepository transactionRepository;
    private final OrderRepository orderRepository;

    public PaymentService(PaymentTransactionRepository transactionRepository,
                          OrderRepository orderRepository) {
        this.transactionRepository = transactionRepository;
        this.orderRepository = orderRepository;
    }

    // ========== Create Payment ==========

    @Transactional
    public PaymentCreateResponseDTO createPayment(String email, CreatePaymentRequestDTO request) {
        OrderEntity order = orderRepository.findById(request.orderId())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy đơn hàng"));

        // Verify order belongs to user
        if (!order.getUser().getEmail().equals(email)) {
            throw new RuntimeException("Đơn hàng không thuộc về bạn");
        }

        // Check if already PAID
        if (order.getPaymentStatus() == PaymentStatus.PAID) {
            throw new RuntimeException("Đơn hàng đã được thanh toán");
        }

        String provider = request.provider() != null ? request.provider().toUpperCase() : "VNPAY";
        String providerPaymentId = provider + "-" + UUID.randomUUID().toString().substring(0, 12).toUpperCase();

        // Create PENDING transaction
        PaymentTransactionEntity transaction = PaymentTransactionEntity.builder()
                .order(order)
                .provider(provider)
                .providerPaymentId(providerPaymentId)
                .amount(order.getTotal())
                .status(PaymentTransactionStatus.PENDING)
                .build();

        transactionRepository.save(transaction);

        // Simulate payment URL from provider
        String paymentUrl = generatePaymentUrl(provider, providerPaymentId, order);

        return new PaymentCreateResponseDTO(
                transaction.getId(),
                paymentUrl,
                provider,
                "Vui lòng thanh toán qua " + provider
        );
    }

    // ========== Webhook Handler ==========

    @Transactional
    public PaymentTransactionResponseDTO handleWebhook(Map<String, String> payload) {
        String providerPaymentId = payload.get("providerPaymentId");
        String status = payload.get("status");               // SUCCESS or FAILED
        String signature = payload.get("signature");

        if (providerPaymentId == null || status == null) {
            throw new RuntimeException("Dữ liệu webhook không hợp lệ");
        }

        // Verify signature (simulated)
        if (!verifySignature(payload, signature)) {
            throw new RuntimeException("Chữ ký không hợp lệ");
        }

        PaymentTransactionEntity transaction = transactionRepository
                .findByProviderPaymentId(providerPaymentId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy giao dịch"));

        // Only update if still PENDING
        if (transaction.getStatus() != PaymentTransactionStatus.PENDING) {
            return toResponseDTO(transaction);
        }

        if ("SUCCESS".equalsIgnoreCase(status)) {
            transaction.setStatus(PaymentTransactionStatus.SUCCESS);

            // Update order payment status
            OrderEntity order = transaction.getOrder();
            order.setPaymentStatus(PaymentStatus.PAID);
            order.setPaidAt(LocalDateTime.now());
            orderRepository.save(order);

        } else {
            transaction.setStatus(PaymentTransactionStatus.FAILED);
        }

        transactionRepository.save(transaction);
        return toResponseDTO(transaction);
    }

    // ========== Query ==========

    public List<PaymentTransactionResponseDTO> getByOrderId(Integer orderId) {
        return transactionRepository.findByOrderId(orderId)
                .stream().map(this::toResponseDTO).toList();
    }

    // ========== Helpers ==========

    /**
     * Simulates payment URL generation for VNPay/MoMo.
     * In production, this would call the actual provider's API.
     */
    private String generatePaymentUrl(String provider, String paymentId, OrderEntity order) {
        return switch (provider) {
            case "VNPAY" ->
                    "https://sandbox.vnpayment.vn/paymentv2/vpcpay.html"
                    + "?vnp_TxnRef=" + paymentId
                    + "&vnp_Amount=" + order.getTotal().longValue() * 100
                    + "&vnp_OrderInfo=Thanh+toan+don+" + order.getCode();
            case "MOMO" ->
                    "https://test-payment.momo.vn/v2/gateway/pay"
                    + "?orderId=" + paymentId
                    + "&amount=" + order.getTotal().longValue()
                    + "&orderInfo=Thanh+toan+don+" + order.getCode();
            default ->
                    "https://payment.example.com/pay"
                    + "?txn=" + paymentId
                    + "&amount=" + order.getTotal().longValue();
        };
    }

    /**
     * Simulates signature verification.
     * In production, this would use HMAC-SHA256 with the provider's secret key.
     */
    private boolean verifySignature(Map<String, String> payload, String signature) {
        // Simulation: always valid if signature is not empty
        return signature != null && !signature.isBlank();
    }

    private PaymentTransactionResponseDTO toResponseDTO(PaymentTransactionEntity t) {
        return new PaymentTransactionResponseDTO(
                t.getId(), t.getOrder().getId(),
                t.getProvider(), t.getProviderPaymentId(),
                t.getAmount(), t.getStatus(), t.getCreatedAt()
        );
    }
}
