package org.example.electronics.controller;

import jakarta.validation.Valid;
import org.example.electronics.dto.request.CheckoutRequestDTO;
import org.example.electronics.dto.request.ShippingRequestDTO;
import org.example.electronics.dto.response.ApiResponse;
import org.example.electronics.dto.response.OrderResponseDTO;
import org.example.electronics.entity.enums.OrderStatus;
import org.example.electronics.security.RequirePermission;
import org.example.electronics.service.OrderService;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    // ==================== Customer ====================

    @PostMapping("/api/orders")
    public ResponseEntity<ApiResponse<OrderResponseDTO>> checkout(
            @AuthenticationPrincipal UserDetails userDetails,
            @Valid @RequestBody CheckoutRequestDTO request) {
        OrderResponseDTO response = orderService.checkout(userDetails.getUsername(), request);
        return ResponseEntity.ok(ApiResponse.success("Đặt hàng thành công", response));
    }

    @GetMapping("/api/orders/me")
    public ResponseEntity<ApiResponse<List<OrderResponseDTO>>> getMyOrders(
            @AuthenticationPrincipal UserDetails userDetails) {
        List<OrderResponseDTO> orders = orderService.getUserOrders(userDetails.getUsername());
        return ResponseEntity.ok(ApiResponse.success(orders));
    }

    // ==================== Admin ====================

    @GetMapping("/api/admin/orders")
    @RequirePermission("MANAGE_ORDER")
    public ResponseEntity<ApiResponse<Page<OrderResponseDTO>>> getAll(
            @RequestParam(required = false) OrderStatus status,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime from,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime to,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        Page<OrderResponseDTO> result = orderService.getAll(status, from, to, page, size);
        return ResponseEntity.ok(ApiResponse.success(result));
    }

    @GetMapping("/api/admin/orders/{id}")
    @RequirePermission("MANAGE_ORDER")
    public ResponseEntity<ApiResponse<OrderResponseDTO>> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(ApiResponse.success(orderService.getById(id)));
    }

    // ========== Order Lifecycle (Admin) ==========

    @PutMapping("/api/admin/orders/{id}/confirm")
    @RequirePermission("MANAGE_ORDER")
    public ResponseEntity<ApiResponse<OrderResponseDTO>> confirmOrder(@PathVariable Integer id) {
        OrderResponseDTO response = orderService.confirmOrder(id);
        return ResponseEntity.ok(ApiResponse.success("Xác nhận đơn hàng thành công", response));
    }

    @PutMapping("/api/admin/orders/{id}/cancel")
    @RequirePermission("MANAGE_ORDER")
    public ResponseEntity<ApiResponse<OrderResponseDTO>> cancelOrder(@PathVariable Integer id) {
        OrderResponseDTO response = orderService.cancelOrder(id);
        return ResponseEntity.ok(ApiResponse.success("Hủy đơn hàng thành công", response));
    }

    @PutMapping("/api/admin/orders/{id}/complete")
    @RequirePermission("MANAGE_ORDER")
    public ResponseEntity<ApiResponse<OrderResponseDTO>> completeOrder(
            @PathVariable Integer id,
            @RequestParam(required = false) Integer warehouseId) {
        OrderResponseDTO response = orderService.completeOrder(id, warehouseId);
        return ResponseEntity.ok(ApiResponse.success("Hoàn thành đơn hàng thành công", response));
    }

    // ========== Shipping (Admin) ==========

    @PutMapping("/api/admin/orders/{id}/shipping")
    @RequirePermission("MANAGE_ORDER")
    public ResponseEntity<ApiResponse<OrderResponseDTO>> updateShipping(
            @PathVariable Integer id,
            @RequestBody ShippingRequestDTO request) {
        OrderResponseDTO response = orderService.updateShipping(id, request);
        return ResponseEntity.ok(ApiResponse.success("Cập nhật vận chuyển thành công", response));
    }
}
