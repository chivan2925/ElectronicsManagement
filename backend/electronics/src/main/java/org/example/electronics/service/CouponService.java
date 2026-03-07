package org.example.electronics.service;

import org.example.electronics.dto.request.CouponRequestDTO;
import org.example.electronics.dto.response.CouponResponseDTO;
import org.example.electronics.dto.response.CouponValidateResponseDTO;
import org.example.electronics.entity.BrandEntity;
import org.example.electronics.entity.CategoryEntity;
import org.example.electronics.entity.CouponEntity;
import org.example.electronics.entity.enums.CouponType;
import org.example.electronics.entity.enums.ProductStatus;
import org.example.electronics.repository.BrandRepository;
import org.example.electronics.repository.CategoryRepository;
import org.example.electronics.repository.CouponRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class CouponService {

    private final CouponRepository couponRepository;
    private final CategoryRepository categoryRepository;
    private final BrandRepository brandRepository;

    public CouponService(CouponRepository couponRepository,
                         CategoryRepository categoryRepository,
                         BrandRepository brandRepository) {
        this.couponRepository = couponRepository;
        this.categoryRepository = categoryRepository;
        this.brandRepository = brandRepository;
    }

    // ========== Validate ==========

    public CouponValidateResponseDTO validate(String code, BigDecimal orderTotal) {
        CouponEntity coupon = couponRepository.findByCode(code).orElse(null);

        if (coupon == null) {
            return new CouponValidateResponseDTO(false, code, "Mã giảm giá không tồn tại", BigDecimal.ZERO);
        }
        if (coupon.getStatus() != ProductStatus.ACTIVE) {
            return new CouponValidateResponseDTO(false, code, "Mã giảm giá không còn hoạt động", BigDecimal.ZERO);
        }

        LocalDateTime now = LocalDateTime.now();
        if (coupon.getStartsAt() != null && now.isBefore(coupon.getStartsAt())) {
            return new CouponValidateResponseDTO(false, code, "Mã giảm giá chưa bắt đầu", BigDecimal.ZERO);
        }
        if (coupon.getEndsAt() != null && now.isAfter(coupon.getEndsAt())) {
            return new CouponValidateResponseDTO(false, code, "Mã giảm giá đã hết hạn", BigDecimal.ZERO);
        }
        if (coupon.getUsageLimit() != null && coupon.getUsedCount() >= coupon.getUsageLimit()) {
            return new CouponValidateResponseDTO(false, code, "Mã giảm giá đã hết lượt sử dụng", BigDecimal.ZERO);
        }
        if (coupon.getMinOrder() != null && orderTotal.compareTo(coupon.getMinOrder()) < 0) {
            return new CouponValidateResponseDTO(false, code,
                    "Đơn hàng tối thiểu " + coupon.getMinOrder() + " để sử dụng mã này", BigDecimal.ZERO);
        }

        BigDecimal discountAmount = calculateDiscount(coupon, orderTotal);
        return new CouponValidateResponseDTO(true, code, "Áp dụng thành công", discountAmount);
    }

    public BigDecimal calculateDiscount(CouponEntity coupon, BigDecimal orderTotal) {
        BigDecimal discount;
        if (coupon.getType() == CouponType.PERCENT) {
            discount = orderTotal.multiply(coupon.getValue())
                    .divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);
        } else {
            discount = coupon.getValue();
        }

        // Cap at maxDiscount if set
        if (coupon.getMaxDiscount() != null && discount.compareTo(coupon.getMaxDiscount()) > 0) {
            discount = coupon.getMaxDiscount();
        }

        // Discount cannot exceed order total
        if (discount.compareTo(orderTotal) > 0) {
            discount = orderTotal;
        }

        return discount;
    }

    // ========== Admin CRUD ==========

    public List<CouponResponseDTO> getAll() {
        return couponRepository.findAll().stream().map(this::toResponseDTO).toList();
    }

    public CouponResponseDTO getById(Integer id) {
        return toResponseDTO(findById(id));
    }

    public CouponResponseDTO create(CouponRequestDTO request) {
        if (couponRepository.existsByCode(request.code())) {
            throw new RuntimeException("Mã coupon đã tồn tại");
        }

        CouponEntity coupon = CouponEntity.builder()
                .code(request.code().toUpperCase())
                .type(request.type())
                .value(request.value())
                .minOrder(request.minOrder())
                .maxDiscount(request.maxDiscount())
                .startsAt(request.startsAt())
                .endsAt(request.endsAt())
                .usageLimit(request.usageLimit())
                .build();

        if (request.categoryId() != null) {
            CategoryEntity cat = categoryRepository.findById(request.categoryId())
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy danh mục"));
            coupon.setCategory(cat);
        }
        if (request.brandId() != null) {
            BrandEntity brand = brandRepository.findById(request.brandId())
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy thương hiệu"));
            coupon.setBrand(brand);
        }

        couponRepository.save(coupon);
        return toResponseDTO(coupon);
    }

    public CouponResponseDTO update(Integer id, CouponRequestDTO request) {
        CouponEntity coupon = findById(id);
        coupon.setCode(request.code().toUpperCase());
        coupon.setType(request.type());
        coupon.setValue(request.value());
        coupon.setMinOrder(request.minOrder());
        coupon.setMaxDiscount(request.maxDiscount());
        coupon.setStartsAt(request.startsAt());
        coupon.setEndsAt(request.endsAt());
        coupon.setUsageLimit(request.usageLimit());

        if (request.categoryId() != null) {
            CategoryEntity cat = categoryRepository.findById(request.categoryId())
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy danh mục"));
            coupon.setCategory(cat);
        } else {
            coupon.setCategory(null);
        }
        if (request.brandId() != null) {
            BrandEntity brand = brandRepository.findById(request.brandId())
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy thương hiệu"));
            coupon.setBrand(brand);
        } else {
            coupon.setBrand(null);
        }

        couponRepository.save(coupon);
        return toResponseDTO(coupon);
    }

    public void delete(Integer id) {
        CouponEntity coupon = findById(id);
        couponRepository.delete(coupon);
    }

    // ========== Helpers ==========

    private CouponEntity findById(Integer id) {
        return couponRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy coupon"));
    }

    private CouponResponseDTO toResponseDTO(CouponEntity c) {
        return new CouponResponseDTO(
                c.getId(), c.getCode(), c.getType(),
                c.getValue(), c.getMinOrder(), c.getMaxDiscount(),
                c.getStartsAt(), c.getEndsAt(),
                c.getUsageLimit(), c.getUsedCount(), c.getStatus(),
                c.getCategory() != null ? c.getCategory().getId() : null,
                c.getBrand() != null ? c.getBrand().getId() : null,
                c.getCreatedAt()
        );
    }
}
