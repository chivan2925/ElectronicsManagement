package org.example.electronics.entity;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class CartDetailId implements Serializable {
    private Integer cart;
    private Integer variant;
}
