package org.example.electronics.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.example.electronics.entity.enums.WarehouseStatus;
import org.hibernate.annotations.Check;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "warehouses")
@SuppressWarnings("deprecation")
@Check(constraints = "current_stock <= capacity")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WarehouseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "Tên kho không được để trống")
    @Column(nullable = false)
    private String name;

    @NotBlank(message = "Địa chỉ cụ thể không được để trống")
    @Column(nullable = false)
    private String line;

    @NotBlank(message = "Phường/Xã không được để trống")
    @Column(nullable = false, length = 50)
    private String ward;

    @NotBlank(message = "Quận/Huyện không được để trống")
    @Column(nullable = false, length = 50)
    private String district;

    @NotBlank(message = "Tỉnh/Thành phố không được để trống")
    @Column(nullable = false, length = 50)
    private String province;

    @NotNull(message = "Sức chứa không được để trống")
    @Min(value = 0, message = "Sức chứa không được phép âm")
    @Column(nullable = false)
    private Integer capacity;

    @Min(value = 0, message = "Tồn kho hiện tại không được phép âm")
    @Column(name = "current_stock", nullable = false)
    @Builder.Default
    private Integer currentStock = 0;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    @Builder.Default
    private WarehouseStatus status = WarehouseStatus.ACTIVE;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false, nullable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;
}