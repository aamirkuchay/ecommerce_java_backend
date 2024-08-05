package com.ecommerce.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor
@Entity
@Table(name = "sku_weights")
public class SKUWeight {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "sku_id", nullable = false)
    private ProductSKU sku;

    @ManyToOne
    @JoinColumn(name = "weight_id", nullable = false)
    private Weight weight;

    @Column(nullable = false)
    private Integer quantity;}
