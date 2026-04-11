package org.example.electronics.service.admin.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.example.electronics.dto.request.admin.order.AdminUpdateOrderRequestDTO;
import org.example.electronics.dto.response.admin.order.AdminOrderDetailResponseDTO;
import org.example.electronics.dto.response.admin.order.AdminOrderResponseDTO;
import org.example.electronics.entity.StaffEntity;
import org.example.electronics.entity.enums.*;
import org.example.electronics.entity.order.OrderEntity;
import org.example.electronics.mapper.OrderMapper;
import org.example.electronics.repository.OrderRepository;
import org.example.electronics.repository.StaffRepository;
import org.example.electronics.service.admin.AdminOrderService;
import org.example.electronics.service.admin.AdminWarehouseService;
import org.example.electronics.util.DateTimeUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AdminOrderServiceImpl implements AdminOrderService {

    private final OrderMapper orderMapper;
    private final OrderRepository orderRepository;
    private final StaffRepository staffRepository;
    private final AdminWarehouseService adminWarehouseService;

    private static final Map<OrderStatus, Set<OrderStatus>> ALLOWED_ORDER_TRANSITIONS = Map.of(
            OrderStatus.PENDING, Set.of(OrderStatus.PROCESSING, OrderStatus.CANCELLED),
            OrderStatus.PROCESSING, Set.of(OrderStatus.COMPLETED, OrderStatus.CANCELLED),
            OrderStatus.COMPLETED, Set.of(OrderStatus.RETURNED),
            OrderStatus.RETURNED, Set.of(OrderStatus.REFUNDED),

            OrderStatus.CANCELLED, Set.of(),
            OrderStatus.REFUNDED, Set.of()
    );

    private static final Map<PaymentStatus, Set<PaymentStatus>> ALLOWED_PAYMENT_TRANSITIONS = Map.of(
            PaymentStatus.PENDING, Set.of(PaymentStatus.PAID, PaymentStatus.FAILED),
            PaymentStatus.PAID, Set.of(PaymentStatus.REFUNDED),

            PaymentStatus.FAILED, Set.of(),
            PaymentStatus.REFUNDED, Set.of()
    );

    private static final Map<ShippingStatus, Set<ShippingStatus>> ALLOWED_SHIPPING_TRANSITIONS = Map.of(
            ShippingStatus.PENDING, Set.of(ShippingStatus.SHIPPING, ShippingStatus.CANCELLED),
            ShippingStatus.SHIPPING, Set.of(ShippingStatus.DELIVERED, ShippingStatus.RETURNED, ShippingStatus.CANCELLED),
            ShippingStatus.DELIVERED, Set.of(ShippingStatus.RETURNED),

            ShippingStatus.RETURNED, Set.of(),
            ShippingStatus.CANCELLED, Set.of()
    );

    @Transactional
    @Override
    public AdminOrderResponseDTO updateOrder(Integer orderId, AdminUpdateOrderRequestDTO adminUpdateOrderRequestDTO, Integer staffId) {
        StaffEntity currentStaffProxy = staffRepository.getReferenceById(staffId);

        OrderEntity existingOrderEntity = orderRepository.findOrderByIdWithDetails(orderId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Không tìm thấy đơn hàng với id: " + orderId
                ));

        checkOrderEditable(existingOrderEntity);

        validateAllTransition(existingOrderEntity, adminUpdateOrderRequestDTO);

        boolean isShipping = adminUpdateOrderRequestDTO.shippingStatus() == ShippingStatus.SHIPPING ||
                              adminUpdateOrderRequestDTO.shippingStatus() == ShippingStatus.DELIVERED;

        if(isShipping && !StringUtils.hasText(adminUpdateOrderRequestDTO.trackingCode())) {
            throw new IllegalArgumentException("Phải nhập mã vận đơn khi trạng thái giao hàng là ĐANG GIAO hoặc ĐÃ GIAO");
        }

        boolean isNowCancelledOrReturned = adminUpdateOrderRequestDTO.status() == OrderStatus.CANCELLED ||
                                        adminUpdateOrderRequestDTO.status() == OrderStatus.RETURNED;

        if (isNowCancelledOrReturned) {
            adminWarehouseService.processCancelledAndReturnedOrder(existingOrderEntity, currentStaffProxy);
        }

        existingOrderEntity.setStatus(adminUpdateOrderRequestDTO.status());
        existingOrderEntity.setPaymentStatus(adminUpdateOrderRequestDTO.paymentStatus());
        existingOrderEntity.setShippingProvider(adminUpdateOrderRequestDTO.shippingProvider());
        existingOrderEntity.setShippingStatus(adminUpdateOrderRequestDTO.shippingStatus());

        if (StringUtils.hasText(adminUpdateOrderRequestDTO.trackingCode())) {
            existingOrderEntity.setTrackingCode(adminUpdateOrderRequestDTO.trackingCode());
        }

        if (adminUpdateOrderRequestDTO.paymentStatus() == PaymentStatus.PAID && existingOrderEntity.getPaidAt() == null) {
            existingOrderEntity.setPaidAt(LocalDateTime.now());
        }

        existingOrderEntity = orderRepository.save(existingOrderEntity);

        return orderMapper.toAdminResponseDTO(existingOrderEntity);
    }

    @Transactional(readOnly = true)
    @Override
    public Page<AdminOrderResponseDTO> getAllOrders(String keyword, OrderStatus status, PaymentMethodType type,
                                                    PaymentStatus paymentStatus, ShippingProvider provider, ShippingStatus shippingStatus,
                                                    LocalDate fromDate, LocalDate toDate, Pageable pageable) {
        LocalDateTime startDateTime = DateTimeUtils.getStartOfDay(fromDate);
        LocalDateTime endDateTime = DateTimeUtils.getEndOfDay(toDate);

        String finalKeyword = StringUtils.hasText(keyword) ? keyword.trim() : null;

        Page<OrderEntity> orderEntityPage = orderRepository.findOrdersWithFilter(finalKeyword, status, type, paymentStatus, provider, shippingStatus, startDateTime, endDateTime, pageable);

        return orderEntityPage.map(orderMapper::toAdminResponseDTO);
    }

    @Transactional(readOnly = true)
    @Override
    public AdminOrderDetailResponseDTO getOrderById(Integer orderId) {
        OrderEntity existingOrderEntity = orderRepository.findOrderByIdWithDetails(orderId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Không tìm thấy đơn hàng với id: " + orderId
                ));

        return orderMapper.toAdminDetailResponseDTO(existingOrderEntity);
    }

    private void validateOrderStatusTransition(OrderStatus currentStatus, OrderStatus newStatus) {
        if (currentStatus == newStatus) {
            return;
        }

        Set<OrderStatus> allowedNextStates = ALLOWED_ORDER_TRANSITIONS.getOrDefault(currentStatus, Set.of());

        if (!allowedNextStates.contains(newStatus)) {
            throw new IllegalArgumentException(
                    String.format("Chuyển đổi trạng thái đơn hàng không hợp lệ, không thể chuyển từ '%s' sang '%s'", currentStatus, newStatus)
            );
        }
    }

    private void validatePaymentStatusTransition(PaymentStatus currentStatus, PaymentStatus newStatus) {
        if (currentStatus == newStatus) {
            return;
        }

        Set<PaymentStatus> allowedNextStates = ALLOWED_PAYMENT_TRANSITIONS.getOrDefault(currentStatus, Set.of());

        if (!allowedNextStates.contains(newStatus)) {
            throw new IllegalArgumentException(
                    String.format("Chuyển đổi trạng thái thanh toán không hợp lệ, không thể chuyển từ '%s' sang '%s'", currentStatus, newStatus)
            );
        }
    }

    private void validateShippingTransition(OrderEntity currentOrder, AdminUpdateOrderRequestDTO adminUpdateOrderRequestDTO) {
        ShippingStatus currentStatus = currentOrder.getShippingStatus();
        ShippingStatus newStatus = adminUpdateOrderRequestDTO.shippingStatus();

        ShippingProvider currentProvider = currentOrder.getShippingProvider();
        ShippingProvider newProvider = adminUpdateOrderRequestDTO.shippingProvider();

        if (currentProvider != newProvider) {
            if (currentStatus != ShippingStatus.PENDING) {
                throw new IllegalArgumentException(
                        "Hàng đang hoặc đã giao (" + currentStatus + "), không thể đổi đơn vị vận chuyển từ " + currentProvider + " sang " + newProvider
                );
            }
        }

        if (currentStatus == newStatus) {
            return;
        }

        Set<ShippingStatus> allowedNextStates = ALLOWED_SHIPPING_TRANSITIONS.getOrDefault(currentStatus, Set.of());

        if (!allowedNextStates.contains(newStatus)) {
            throw new IllegalArgumentException(
                    String.format("Chuyển đổi trạng thái vận chuyển không hợp lệ, không thể chuyển từ '%s' sang '%s'", currentStatus, newStatus)
            );
        }
    }

    private void validateAllTransition(OrderEntity currentOrder, AdminUpdateOrderRequestDTO adminUpdateOrderRequestDTO) {
        validateOrderStatusTransition(currentOrder.getStatus(), adminUpdateOrderRequestDTO.status());
        validatePaymentStatusTransition(currentOrder.getPaymentStatus(), adminUpdateOrderRequestDTO.paymentStatus());
        validateShippingTransition(currentOrder, adminUpdateOrderRequestDTO);
    }

    private void checkOrderEditable(OrderEntity order) {
        if (order.getStatus() != OrderStatus.PENDING) {
            throw new IllegalStateException(
                    "Không thể chỉnh sửa nội dung! Đơn hàng đang ở trạng thái " + order.getStatus() + ". Chỉ được phép sửa khi đang PENDING."
            );
        }
    }
}
