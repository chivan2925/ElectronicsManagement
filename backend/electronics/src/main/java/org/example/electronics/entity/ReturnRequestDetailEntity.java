package org.example.electronics.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "return_request_details")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReturnRequestDetailEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "return_request_id", nullable = false)
    private ReturnRequestEntity returnRequest;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({
        @JoinColumn(name = "order_id", referencedColumnName = "order_id", nullable = false),
        @JoinColumn(name = "variant_id", referencedColumnName = "variant_id", nullable = false)
    })
    private OrderDetailEntity orderDetail;

    @Column(nullable = false)
    private Integer quantity;
}
