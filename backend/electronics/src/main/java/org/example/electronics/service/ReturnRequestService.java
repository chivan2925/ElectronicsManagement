package org.example.electronics.service;

import org.example.electronics.dto.request.CreateReturnRequestDTO;
import org.example.electronics.dto.response.ReturnRequestItemResponseDTO;
import org.example.electronics.dto.response.ReturnRequestResponseDTO;
import org.example.electronics.entity.*;
import org.example.electronics.entity.enums.OrderStatus;
import org.example.electronics.entity.enums.PaymentStatus;
import org.example.electronics.entity.enums.PaymentTransactionStatus;
import org.example.electronics.entity.enums.ReturnRequestStatus;
import org.example.electronics.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
public class ReturnRequestService {

    private final ReturnRequestRepository returnRepository;
    private final OrderRepository orderRepository;
    private final OrderDetailRepository orderDetailRepository;
    private final PaymentTransactionRepository paymentRepository;
    private final WarehouseTransactionService warehouseTransactionService;
    private final UserRepository userRepository;

    public ReturnRequestService(ReturnRequestRepository returnRepository,
                                OrderRepository orderRepository,
                                OrderDetailRepository orderDetailRepository,
                                PaymentTransactionRepository paymentRepository,
                                WarehouseTransactionService warehouseTransactionService,
                                UserRepository userRepository) {
        this.returnRepository = returnRepository;
        this.orderRepository = orderRepository;
        this.orderDetailRepository = orderDetailRepository;
        this.paymentRepository = paymentRepository;
        this.warehouseTransactionService = warehouseTransactionService;
        this.userRepository = userRepository;
    }

    // ========== User ==========

    @Transactional
    public ReturnRequestResponseDTO createRequest(String email, CreateReturnRequestDTO request) {
        OrderEntity order = orderRepository.findById(request.orderId())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy đơn hàng"));

        if (!order.getUser().getEmail().equals(email)) {
            throw new RuntimeException("Đơn hàng không thuộc về bạn");
        }

        if (order.getStatus() != OrderStatus.COMPLETED) {
            throw new RuntimeException("Chỉ có thể yêu cầu đổi trả khi đơn hàng đã hoàn thành");
        }

        ReturnRequestEntity returnRequest = ReturnRequestEntity.builder()
                .user(order.getUser())
                .order(order)
                .type(request.type())
                .status(ReturnRequestStatus.PENDING)
                .reason(request.reason())
                .evidenceJson(request.evidenceJson())
                .build();

        returnRepository.save(returnRequest);

        for (var itemReq : request.items()) {
            OrderDetailEntity orderDetail = orderDetailRepository.findById(new OrderDetailId(order.getId(), itemReq.variantId()))
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy sản phẩm trong đơn"));

            if (!orderDetail.getOrder().getId().equals(order.getId())) {
                throw new RuntimeException("Sản phẩm không thuộc đơn hàng này");
            }

            if (itemReq.quantity() > orderDetail.getQuantity()) {
                throw new RuntimeException("Số lượng yêu cầu đổi trả vượt quá số lượng đã mua");
            }

            ReturnRequestDetailEntity detail = ReturnRequestDetailEntity.builder()
                    .returnRequest(returnRequest)
                    .orderDetail(orderDetail)
                    .quantity(itemReq.quantity())
                    .build();
            returnRequest.getDetails().add(detail);
        }

        returnRepository.save(returnRequest);
        return toResponseDTO(returnRequest);
    }

    public List<ReturnRequestResponseDTO> getMyRequests(String email) {
        UserEntity user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy người dùng"));
        return returnRepository.findByUserIdOrderByCreatedAtDesc(user.getId()).stream()
                .map(this::toResponseDTO)
                .toList();
    }

    // ========== Admin ==========

    public List<ReturnRequestResponseDTO> getAll() {
        return returnRepository.findAll().stream().map(this::toResponseDTO).toList();
    }

    @Transactional
    public ReturnRequestResponseDTO approve(Integer id, Integer warehouseId) {
        ReturnRequestEntity req = returnRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy yêu cầu"));

        if (req.getStatus() != ReturnRequestStatus.PENDING) {
            throw new RuntimeException("Yêu cầu đã được xử lý");
        }

        req.setStatus(ReturnRequestStatus.APPROVED);

        // Calculate refund amount
        BigDecimal refundAmount = BigDecimal.ZERO;
        for (var detail : req.getDetails()) {
            refundAmount = refundAmount.add(detail.getOrderDetail().getPrice().multiply(BigDecimal.valueOf(detail.getQuantity())));
        }

        // 1. Process Refund
        OrderEntity order = req.getOrder();
        if (order.getPaymentStatus() == PaymentStatus.PAID) {
            PaymentTransactionEntity refundTransaction = PaymentTransactionEntity.builder()
                    .order(order)
                    .provider("SYSTEM_REFUND")
                    .providerPaymentId("REFUND-" + UUID.randomUUID().toString().substring(0, 8))
                    .amount(refundAmount)
                    .status(PaymentTransactionStatus.SUCCESS) // Auto-assume success for logic
                    .build();
            paymentRepository.save(refundTransaction);
            order.setPaymentStatus(PaymentStatus.REFUNDED);
            order.setStatus(OrderStatus.RETURNED);
            orderRepository.save(order);
        }

        // 2. Process Restock (Warehouse IN)
        if (warehouseId != null) {
            // Re-using transaction service to create IN transaction
            org.example.electronics.dto.request.WarehouseTransactionRequestDTO whReq =
                    new org.example.electronics.dto.request.WarehouseTransactionRequestDTO(
                            warehouseId,
                            org.example.electronics.entity.enums.WarehouseTransactionType.IN,
                            "Nhập kho từ yêu cầu đổi/trả #" + req.getId(),
                            null,
                            req.getId(),
                            req.getDetails().stream().map(d ->
                                    new org.example.electronics.dto.request.WarehouseTransactionItemDTO(
                                            d.getOrderDetail().getVariant().getId(),
                                            d.getQuantity()
                                    )).toList()
                    );
            var whTransaction = warehouseTransactionService.create(whReq);
            // Auto approve
            warehouseTransactionService.approve(whTransaction.id());
        }

        returnRepository.save(req);
        return toResponseDTO(req);
    }

    public ReturnRequestResponseDTO reject(Integer id) {
        ReturnRequestEntity req = returnRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy yêu cầu"));
        req.setStatus(ReturnRequestStatus.REJECTED);
        returnRepository.save(req);
        return toResponseDTO(req);
    }

    public ReturnRequestResponseDTO cancel(Integer id) {
        ReturnRequestEntity req = returnRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy yêu cầu"));
        req.setStatus(ReturnRequestStatus.CANCELLED);
        returnRepository.save(req);
        return toResponseDTO(req);
    }

    // ========== Helpers ==========

    private ReturnRequestResponseDTO toResponseDTO(ReturnRequestEntity r) {
        List<ReturnRequestItemResponseDTO> items = r.getDetails() != null
                ? r.getDetails().stream().map(d -> new ReturnRequestItemResponseDTO(
                    d.getOrderDetail().getVariant().getId(),
                    d.getOrderDetail().getVariant().getId(),
                    d.getOrderDetail().getVariant().getName(),
                    d.getOrderDetail().getVariant().getProduct().getName(),
                    d.getQuantity()
                )).toList()
                : List.of();

        return new ReturnRequestResponseDTO(
                r.getId(), r.getOrder().getId(), r.getOrder().getCode(),
                r.getType(), r.getStatus(), r.getReason(), r.getEvidenceJson(),
                items, r.getCreatedAt()
        );
    }
}
