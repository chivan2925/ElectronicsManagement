package org.example.electronics.service;

import org.example.electronics.dto.request.CheckoutRequestDTO;
import org.example.electronics.dto.request.ShippingRequestDTO;
import org.example.electronics.dto.response.OrderItemResponseDTO;
import org.example.electronics.dto.response.OrderResponseDTO;
import org.example.electronics.entity.*;
import org.example.electronics.entity.enums.OrderStatus;
import org.example.electronics.entity.enums.PaymentStatus;
import org.example.electronics.entity.enums.ShippingStatus;
import org.example.electronics.repository.*;
import org.example.electronics.specification.OrderSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final CartRepository cartRepository;
    private final CartDetailRepository cartDetailRepository;
    private final AddressRepository addressRepository;
    private final VariantRepository variantRepository;
    private final CouponRepository couponRepository;
    private final CouponService couponService;
    private final WarehouseTransactionService warehouseTransactionService;

    public OrderService(OrderRepository orderRepository,
                        UserRepository userRepository,
                        CartRepository cartRepository,
                        CartDetailRepository cartDetailRepository,
                        AddressRepository addressRepository,
                        VariantRepository variantRepository,
                        CouponRepository couponRepository,
                        CouponService couponService,
                        WarehouseTransactionService warehouseTransactionService) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.cartRepository = cartRepository;
        this.cartDetailRepository = cartDetailRepository;
        this.addressRepository = addressRepository;
        this.variantRepository = variantRepository;
        this.couponRepository = couponRepository;
        this.couponService = couponService;
        this.warehouseTransactionService = warehouseTransactionService;
    }

    @Transactional
    public OrderResponseDTO checkout(String email, CheckoutRequestDTO request) {
        UserEntity user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy người dùng"));

        // 1. Load cart
        CartEntity cart = cartRepository.findByUserId(user.getId())
                .orElseThrow(() -> new RuntimeException("Giỏ hàng trống"));
        List<CartDetailEntity> cartItems = cartDetailRepository.findByCartId(cart.getId());
        if (cartItems.isEmpty()) {
            throw new RuntimeException("Giỏ hàng trống");
        }

        // 2. Resolve address
        AddressEntity address = addressRepository.findById(request.addressId())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy địa chỉ"));
        String fullAddress = address.getReceiverName() + ", " + address.getPhone()
                + ", " + address.getLine()
                + (address.getWard() != null ? ", " + address.getWard() : "")
                + (address.getDistrict() != null ? ", " + address.getDistrict() : "")
                + (address.getProvince() != null ? ", " + address.getProvince() : "");

        // 3. Calculate subtotal & validate stock
        BigDecimal subtotal = BigDecimal.ZERO;
        for (CartDetailEntity item : cartItems) {
            VariantEntity variant = item.getVariant();
            if (variant.getStock() < item.getQuantity()) {
                throw new RuntimeException("Sản phẩm '" + variant.getName() + "' không đủ tồn kho");
            }
            subtotal = subtotal.add(variant.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())));
        }

        // 4. Apply coupon if present
        BigDecimal discount = BigDecimal.ZERO;
        CouponEntity coupon = null;
        if (request.couponCode() != null && !request.couponCode().isBlank()) {
            coupon = couponRepository.findByCode(request.couponCode())
                    .orElseThrow(() -> new RuntimeException("Mã giảm giá không tồn tại"));

            var validation = couponService.validate(request.couponCode(), subtotal);
            if (!validation.valid()) {
                throw new RuntimeException(validation.message());
            }
            discount = validation.discountAmount();
        }

        // 5. Shipping fee (default logic, can be enhanced later)
        BigDecimal shippingFee = BigDecimal.valueOf(30000); // 30k VND default

        // 6. Total
        BigDecimal total = subtotal.subtract(discount).add(shippingFee);
        if (total.compareTo(BigDecimal.ZERO) < 0) {
            total = BigDecimal.ZERO;
        }

        // 7. Create order
        String orderCode = "ORD-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();

        OrderEntity order = OrderEntity.builder()
                .user(user)
                .code(orderCode)
                .address(fullAddress)
                .paymentMethod(request.paymentMethod())
                .subtotal(subtotal)
                .discount(discount)
                .shippingFee(shippingFee)
                .total(total)
                .note(request.note())
                .build();

        orderRepository.save(order);

        // 8. Create order details & reduce stock
        for (CartDetailEntity cartItem : cartItems) {
            VariantEntity variant = cartItem.getVariant();

            OrderDetailEntity detail = OrderDetailEntity.builder()
                    .order(order)
                    .variant(variant)
                    .quantity(cartItem.getQuantity())
                    .price(variant.getPrice())
                    .build();
            order.getOrderDetails().add(detail);

            // Reduce stock
            variant.setStock(variant.getStock() - cartItem.getQuantity());
            variantRepository.save(variant);
        }

        orderRepository.save(order);

        // 9. Increment coupon used count
        if (coupon != null) {
            coupon.setUsedCount(coupon.getUsedCount() + 1);
            couponRepository.save(coupon);
        }

        // 10. Clear cart
        cartDetailRepository.deleteByCartId(cart.getId());

        return toResponseDTO(order);
    }

    // ========== User History ==========

    public List<OrderResponseDTO> getUserOrders(String email) {
        UserEntity user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy người dùng"));
        return orderRepository.findByUserIdOrderByCreatedAtDesc(user.getId())
                .stream().map(this::toResponseDTO).toList();
    }

    // ========== Admin ==========

    public Page<OrderResponseDTO> getAll(OrderStatus status, LocalDateTime from, LocalDateTime to,
                                          int page, int size) {
        Specification<OrderEntity> spec = Specification
                .where(OrderSpecification.hasStatus(status))
                .and(OrderSpecification.createdAfter(from))
                .and(OrderSpecification.createdBefore(to));

        return orderRepository.findAll(spec, PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt")))
                .map(this::toResponseDTO);
    }

    public OrderResponseDTO getById(Integer id) {
        OrderEntity order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy đơn hàng"));
        return toResponseDTO(order);
    }

    @Transactional
    public OrderResponseDTO updateStatus(Integer id, OrderStatus newStatus, Integer warehouseId) {
        OrderEntity order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy đơn hàng"));

        OrderStatus oldStatus = order.getStatus();
        order.setStatus(newStatus);
        orderRepository.save(order);

        // When order moves to COMPLETED → auto-create warehouse OUT transaction
        if (newStatus == OrderStatus.COMPLETED && oldStatus != OrderStatus.COMPLETED
                && warehouseId != null) {
            warehouseTransactionService.createFromOrder(warehouseId, order);
        }

        return toResponseDTO(order);
    }

    // ========== Order Lifecycle ==========

    @Transactional
    public OrderResponseDTO confirmOrder(Integer id) {
        OrderEntity order = findOrderById(id);
        validateTransition(order.getStatus(), OrderStatus.CONFIRMED);
        order.setStatus(OrderStatus.CONFIRMED);
        order.setShippingStatus(ShippingStatus.PENDING);
        orderRepository.save(order);
        return toResponseDTO(order);
    }

    @Transactional
    public OrderResponseDTO cancelOrder(Integer id) {
        OrderEntity order = findOrderById(id);
        OrderStatus current = order.getStatus();
        if (current != OrderStatus.PENDING && current != OrderStatus.CONFIRMED) {
            throw new RuntimeException("Không thể hủy đơn hàng ở trạng thái " + current);
        }
        order.setStatus(OrderStatus.CANCELLED);
        order.setShippingStatus(ShippingStatus.CANCELLED);

        // Restore variant stock
        for (OrderDetailEntity detail : order.getOrderDetails()) {
            VariantEntity variant = detail.getVariant();
            variant.setStock(variant.getStock() + detail.getQuantity());
            variantRepository.save(variant);
        }

        orderRepository.save(order);
        return toResponseDTO(order);
    }

    @Transactional
    public OrderResponseDTO completeOrder(Integer id, Integer warehouseId) {
        OrderEntity order = findOrderById(id);
        validateTransition(order.getStatus(), OrderStatus.COMPLETED);
        order.setStatus(OrderStatus.COMPLETED);
        order.setShippingStatus(ShippingStatus.DELIVERED);
        orderRepository.save(order);

        // Auto-create warehouse OUT if warehouseId provided
        if (warehouseId != null) {
            warehouseTransactionService.createFromOrder(warehouseId, order);
        }

        return toResponseDTO(order);
    }

    // ========== Shipping ==========

    @Transactional
    public OrderResponseDTO updateShipping(Integer id, ShippingRequestDTO request) {
        OrderEntity order = findOrderById(id);

        if (request.shippingProvider() != null) {
            order.setShippingProvider(request.shippingProvider());
        }
        if (request.trackingCode() != null) {
            order.setTrackingCode(request.trackingCode());
        }
        if (request.shippingStatus() != null) {
            order.setShippingStatus(request.shippingStatus());

            // Sync order status with shipping status
            if (request.shippingStatus() == ShippingStatus.SHIPPING
                    && order.getStatus() == OrderStatus.CONFIRMED) {
                order.setStatus(OrderStatus.SHIPPING);
            } else if (request.shippingStatus() == ShippingStatus.DELIVERED
                    && order.getStatus() == OrderStatus.SHIPPING) {
                order.setStatus(OrderStatus.COMPLETED);
            }
        }

        orderRepository.save(order);
        return toResponseDTO(order);
    }

    // ========== State Machine Validation ==========

    private void validateTransition(OrderStatus current, OrderStatus target) {
        boolean valid = switch (target) {
            case CONFIRMED -> current == OrderStatus.PENDING;
            case SHIPPING -> current == OrderStatus.CONFIRMED;
            case COMPLETED -> current == OrderStatus.SHIPPING;
            case CANCELLED -> current == OrderStatus.PENDING || current == OrderStatus.CONFIRMED;
            default -> false;
        };
        if (!valid) {
            throw new RuntimeException("Không thể chuyển từ trạng thái " + current + " sang " + target);
        }
    }

    private OrderEntity findOrderById(Integer id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy đơn hàng"));
    }

    // ========== Helpers ==========

    private OrderResponseDTO toResponseDTO(OrderEntity o) {
        List<OrderItemResponseDTO> items = o.getOrderDetails() != null
                ? o.getOrderDetails().stream().map(d -> new OrderItemResponseDTO(
                    d.getVariant().getId(),
                    d.getVariant().getProduct().getName(),
                    d.getVariant().getName(),
                    d.getPrice(),
                    d.getQuantity(),
                    d.getPrice().multiply(BigDecimal.valueOf(d.getQuantity()))
                )).toList()
                : List.of();

        return new OrderResponseDTO(
                o.getId(), o.getCode(), o.getAddress(),
                o.getStatus(), o.getPaymentMethod(), o.getPaymentStatus(),
                o.getDiscount(), o.getShippingFee(), o.getSubtotal(), o.getTotal(),
                o.getNote(), o.getShippingProvider(), o.getShippingStatus(), o.getTrackingCode(),
                items, o.getCreatedAt()
        );
    }
}
