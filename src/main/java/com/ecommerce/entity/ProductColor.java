package com.ecommerce.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor
@Entity
public class ProductColor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private String color;


    @Min(value = 0)
    private Integer quantity;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "product_size_id", nullable = false)
    private ProductSize productSize;

    public ProductColor(ProductSize productSize, String color, Integer quantity) {
        this.productSize = productSize;
        this.color = color;
        this.quantity = quantity;
    }
}
