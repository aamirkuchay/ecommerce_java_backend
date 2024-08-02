package com.ecommerce.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

//@Entity
//@Getter
//@Setter
//@NoArgsConstructor
//@AllArgsConstructor
//public class ProductSize {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @Column(nullable = false)
//    private String size;
//
//    @Column(nullable = false)
//    private Integer quantity;
//
//    @JsonManagedReference
//    @ManyToOne
//    @JoinColumn(name = "product_id", nullable = false)
//    private Product product;
//
//    @OneToMany(mappedBy = "productSize", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<ProductColor> colors = new ArrayList<>();
//
//    public ProductSize(Product product, String size, Integer quantity) {
//        this.product = product;
//        this.size = size;
//        this.quantity = quantity;
//    }
//}
