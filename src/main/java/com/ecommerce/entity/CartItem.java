package com.ecommerce.entity;


import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private Integer quantity;

    @ManyToOne
    @JoinColumn(name = "cart_id", nullable = false)
    private Cart cart;


    @ManyToOne
    @JoinColumn(name = "size_id", nullable = true)
    private Size size;

    @ManyToOne
    @JoinColumn(name = "weight_id",nullable = true)
    private Weight weight;


    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;



    public Long getWeightId() {
        return (weight != null) ? weight.getId() : null;
    }


    public Long getSizeId() {
        return (weight != null) ? weight.getId() : null;
    }


}
