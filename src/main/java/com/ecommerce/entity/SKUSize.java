package com.ecommerce.entity;

import com.ecommerce.exception.ResourceNotFoundException;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

    @Entity @Getter @Setter
    public class SKUSize {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @ManyToOne
        @JoinColumn(name = "sku_id", nullable = false)
        private ProductSKU sku;

        @ManyToOne
        @JoinColumn(name = "size_id", nullable = false)
        private Size size;

        @Column(nullable = false)
        private Integer quantity;



        public void reduceQuantity(Integer quantityToReduce) {
            if (this.quantity < quantityToReduce) {
                throw new ResourceNotFoundException("Not enough quantity available for the selected weight");
            }
            this.quantity -= quantityToReduce;
        }
}
