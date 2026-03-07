package org.example.electronics.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "warehouse_details")
@IdClass(WarehouseDetailId.class)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WarehouseDetailEntity {

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "warehouse_id", nullable = false)
    private WarehouseEntity warehouse;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "variant_id", nullable = false)
    private VariantEntity variant;

    @Column(nullable = false)
    @Builder.Default
    private Integer quantity = 0;
}
