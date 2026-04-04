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

@Service
@RequiredArgsConstructor
public class AdminOrderServiceImpl implements AdminOrderService {

    private final OrderMapper orderMapper;
    private final OrderRepository orderRepository;
    private final StaffRepository staffRepository;
    private final AdminWarehouseService adminWarehouseService;

    @Transactional
    @Override
    public AdminOrderResponseDTO updateOrder(Integer orderId, AdminUpdateOrderRequestDTO adminUpdateOrderRequestDTO, Integer staffId) {
        StaffEntity currentStaffProxy = staffRepository.getReferenceById(staffId);

        OrderEntity existingOrderEntity = orderRepository.findOrderByIdWithDetails(orderId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Không tìm thấy đơn hàng với id: " + orderId
                ));

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

        return orderMapper.toResponseDTO(existingOrderEntity);
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

        return orderEntityPage.map(orderMapper::toResponseDTO);
    }

    @Transactional(readOnly = true)
    @Override
    public AdminOrderDetailResponseDTO getOrderById(Integer orderId) {
        OrderEntity existingOrderEntity = orderRepository.findOrderByIdWithDetails(orderId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Không tìm thấy đơn hàng với id: " + orderId
                ));

        return orderMapper.toDetailResponseDTO(existingOrderEntity);
    }

    private void validateOrderStatusTransition(OrderStatus currentStatus, OrderStatus newStatus) {
        if (currentStatus == newStatus) {
            return;
        }

        if (currentStatus == OrderStatus.CANCELLED || currentStatus == OrderStatus.RETURNED || currentStatus == OrderStatus.REFUNDED) {
            throw new IllegalArgumentException("Đơn hàng đã ở trạng thái " + currentStatus + ", không thể thay đổi trạng thái khác.");
        }

        if (currentStatus.ordinal() > newStatus.ordinal()) {
            throw new IllegalArgumentException("Không được lùi trạng thái đơn hàng từ " + currentStatus + " về " + newStatus);
        }
    }

    private void validatePaymentStatusTransition(PaymentStatus currentStatus, PaymentStatus newStatus) {
        if (currentStatus == newStatus) {
            return;
        }

        if (currentStatus == PaymentStatus.FAILED || currentStatus == PaymentStatus.REFUNDED) {
            throw new IllegalArgumentException("Đơn hàng đã ở trạng thái thanh toán " + currentStatus + ", không thể thay đổi trạng thái khác.");
        }

        if (currentStatus.ordinal() > newStatus.ordinal()) {
            throw new IllegalArgumentException("Không được lùi trạng thái thanh toán đơn hàng từ " + currentStatus + " về " + newStatus);
        }
    }

    private void validateShippingTransition(OrderEntity currentOrder, AdminUpdateOrderRequestDTO adminUpdateOrderRequestDTO) {
        ShippingStatus currentStatus = currentOrder.getShippingStatus();
        ShippingStatus newStatus = adminUpdateOrderRequestDTO.shippingStatus();

        ShippingProvider currentProvider = currentOrder.getShippingProvider();
        ShippingProvider newProvider = adminUpdateOrderRequestDTO.shippingProvider();

        if (currentStatus.ordinal() > newStatus.ordinal()) {
            throw new IllegalArgumentException("Không được lùi trạng thái vận chuyển đơn hàng từ " + currentStatus + " về " + newStatus);
        }

        if ((currentStatus == ShippingStatus.RETURNED || currentStatus == ShippingStatus.CANCELLED) && currentStatus != newStatus) {
            throw new IllegalArgumentException("Luồng vận chuyển đã đóng (" + currentStatus + "), không thể cập nhật.");
        }

        if (currentStatus != ShippingStatus.PENDING && currentProvider != newProvider) {
            throw new IllegalArgumentException(
                    "Hàng đang hoặc đã giao (" + currentStatus +"), " +
                    "không thể đổi đơn vị vận chuyển từ " + currentProvider + " sang " + newProvider
            );
        }
    }

    private void validateAllTransition(OrderEntity currentOrder, AdminUpdateOrderRequestDTO adminUpdateOrderRequestDTO) {
        validateOrderStatusTransition(currentOrder.getStatus(), adminUpdateOrderRequestDTO.status());
        validatePaymentStatusTransition(currentOrder.getPaymentStatus(), adminUpdateOrderRequestDTO.paymentStatus());
        validateShippingTransition(currentOrder, adminUpdateOrderRequestDTO);
    }
}
