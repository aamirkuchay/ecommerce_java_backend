package com.ecommerce.repository;

import com.ecommerce.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem,Long> {


//        @Query("SELECT ci FROM CartItem ci WHERE ci.cart = :cart AND ci.product = :product AND (ci.size IS NULL OR ci.size = :size) AND (ci.weight IS NULL OR ci.weight = :weight)")
//        Optional<CartItem> findByCartAndProductAndSizeAndWeight(Cart cart, Product product, Size size, Weight weight);

    @Query("SELECT ci FROM CartItem ci WHERE ci.cart = :cart AND ci.product = :product AND (:size IS NULL OR ci.size = :size) AND (:weight IS NULL OR ci.weight = :weight)")
    Optional<CartItem> findByCartAndProductAndSizeAndWeight(@Param("cart") Cart cart, @Param("product") Product product, @Param("size") Size size, @Param("weight") Weight weight);




}

//    Optional<CartItem> findByCartAndProductAndSizeAndWeight(Cart cart, Product product, Size size, Weight weight);

