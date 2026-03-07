package org.example.electronics.service;

import org.example.electronics.dto.request.CartItemRequestDTO;
import org.example.electronics.dto.response.CartItemResponseDTO;
import org.example.electronics.dto.response.CartResponseDTO;
import org.example.electronics.entity.*;
import org.example.electronics.repository.CartDetailRepository;
import org.example.electronics.repository.CartRepository;
import org.example.electronics.repository.UserRepository;
import org.example.electronics.repository.VariantRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class CartService {

    private final CartRepository cartRepository;
    private final CartDetailRepository cartDetailRepository;
    private final UserRepository userRepository;
    private final VariantRepository variantRepository;

    public CartService(CartRepository cartRepository,
                       CartDetailRepository cartDetailRepository,
                       UserRepository userRepository,
                       VariantRepository variantRepository) {
        this.cartRepository = cartRepository;
        this.cartDetailRepository = cartDetailRepository;
        this.userRepository = userRepository;
        this.variantRepository = variantRepository;
    }

    public CartResponseDTO getCart(String email) {
        UserEntity user = findUserByEmail(email);
        CartEntity cart = getOrCreateCart(user);
        return toCartResponse(cart);
    }

    @Transactional
    public CartResponseDTO addOrUpdateItem(String email, CartItemRequestDTO request) {
        UserEntity user = findUserByEmail(email);
        CartEntity cart = getOrCreateCart(user);

        VariantEntity variant = variantRepository.findById(request.variantId())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy biến thể sản phẩm"));

        // Validate stock
        if (variant.getStock() < request.quantity()) {
            throw new RuntimeException("Số lượng tồn kho không đủ (còn " + variant.getStock() + ")");
        }

        Optional<CartDetailEntity> existingItem =
                cartDetailRepository.findByCartIdAndVariantId(cart.getId(), variant.getId());

        if (existingItem.isPresent()) {
            CartDetailEntity item = existingItem.get();
            item.setQuantity(request.quantity());
            cartDetailRepository.save(item);
        } else {
            CartDetailEntity newItem = CartDetailEntity.builder()
                    .cart(cart)
                    .variant(variant)
                    .quantity(request.quantity())
                    .build();
            cartDetailRepository.save(newItem);
        }

        // Reload cart
        CartEntity updatedCart = cartRepository.findByUserId(user.getId()).orElse(cart);
        return toCartResponse(updatedCart);
    }

    @Transactional
    public CartResponseDTO removeItem(String email, Integer variantId) {
        UserEntity user = findUserByEmail(email);
        CartEntity cart = cartRepository.findByUserId(user.getId())
                .orElseThrow(() -> new RuntimeException("Giỏ hàng trống"));

        cartDetailRepository.deleteByCartIdAndVariantId(cart.getId(), variantId);

        CartEntity updatedCart = cartRepository.findByUserId(user.getId()).orElse(cart);
        return toCartResponse(updatedCart);
    }

    // ========== Helpers ==========

    CartEntity getOrCreateCart(UserEntity user) {
        return cartRepository.findByUserId(user.getId())
                .orElseGet(() -> {
                    CartEntity newCart = CartEntity.builder().user(user).build();
                    return cartRepository.save(newCart);
                });
    }

    private UserEntity findUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy người dùng"));
    }

    private CartResponseDTO toCartResponse(CartEntity cart) {
        List<CartDetailEntity> details = cartDetailRepository.findByCartId(cart.getId());

        List<CartItemResponseDTO> items = details.stream().map(d -> {
            VariantEntity v = d.getVariant();
            ProductEntity p = v.getProduct();
            BigDecimal subtotal = v.getPrice().multiply(BigDecimal.valueOf(d.getQuantity()));

            String primaryImage = p.getMedia() != null
                    ? p.getMedia().stream()
                        .filter(MediaEntity::getIsPrimary)
                        .findFirst()
                        .map(MediaEntity::getImageUrl)
                        .orElse(null)
                    : null;

            return new CartItemResponseDTO(
                    v.getId(), p.getName(), v.getName(), v.getColor(),
                    v.getPrice(), d.getQuantity(), subtotal, primaryImage
            );
        }).toList();

        BigDecimal totalAmount = items.stream()
                .map(CartItemResponseDTO::subtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        int totalItems = items.stream().mapToInt(CartItemResponseDTO::quantity).sum();

        return new CartResponseDTO(cart.getId(), items, totalAmount, totalItems);
    }
}
