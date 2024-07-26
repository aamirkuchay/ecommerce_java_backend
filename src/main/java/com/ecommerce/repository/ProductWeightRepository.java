package com.ecommerce.repository;

import com.ecommerce.entity.ProductWeight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductWeightRepository extends JpaRepository<ProductWeight,Long> {
}
