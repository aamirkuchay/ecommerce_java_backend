package com.ecommerce.entity;

import com.ecommerce.beans.ProductSKUSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor
@Entity
@JsonSerialize(using = ProductSKUSerializer.class)

public class ProductSKU {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(nullable = false, unique = true)
    private String sku;

    private BigDecimal price;


    @ManyToOne
    @JoinColumn(name = "color_id")
    private ProductColor color;

    @OneToMany(mappedBy = "sku", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SKUSize> sizes = new ArrayList<>();

    @OneToMany(mappedBy = "sku", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SKUWeight> weights = new ArrayList<>();


    @OneToMany(mappedBy = "sku", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SKUAttribute> skuAttributes = new ArrayList<>();

    @Override
    public String toString() {
        return sku;
    }


}
