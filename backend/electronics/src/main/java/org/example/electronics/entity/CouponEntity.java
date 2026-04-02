package org.example.electronics.entity;

import jakarta.persistence.*;
import lombok.*;
import org.example.electronics.entity.enums.CouponStatus;
import org.example.electronics.entity.enums.CouponType;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(
        name = "coupons",
        check = {
                @CheckConstraint(name = "chk_value_non_negative", constraint = "value > 0"),
                @CheckConstraint(name = "chk_min_order_non_negative", constraint = "min_order >= 0"),
                @CheckConstraint(name = "chk_usage_limit_non_negative", constraint = "usage_limit > 0")
        }
)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CouponEntity {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Integer id;

        @ManyToOne
        @JoinColumn(name = "category_id")
        private CategoryEntity category;

        @ManyToOne
        @JoinColumn(name = "brand_id")
        private CategoryEntity brand;

        @Column(nullable = false, length = 20)
        private String code;

        @Enumerated(EnumType.STRING)
        @Column(nullable = false)
        private CouponType type;

        @Column(nullable = false)
        private BigDecimal value;

        @Column(name = "min_order", nullable = false)
        private BigDecimal minOrder;

        @Column(name = "starts_at", nullable = false)
        private LocalDateTime startsAt;

        @Column(name = "ends_at", nullable = false)
        private LocalDateTime endsAt;

        @Column(name = "usage_limit")
        private Integer usageLimit;

        @Column(name = "max_discount")
        private BigDecimal maxDiscount;

        @Enumerated(EnumType.STRING)
        @Column(nullable = false)
        @Builder.Default
        private CouponStatus status = CouponStatus.VALID;

        @CreationTimestamp
        @Column(name = "created_at", updatable = false, nullable = false)
        private LocalDateTime createdAt;

        @UpdateTimestamp
        @Column(name = "updated_at", nullable = false)
        private LocalDateTime updatedAt;
}
