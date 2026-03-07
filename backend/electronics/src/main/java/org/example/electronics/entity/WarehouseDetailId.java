package org.example.electronics.entity;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class WarehouseDetailId implements Serializable {
    private Integer warehouse;
    private Integer variant;
}
