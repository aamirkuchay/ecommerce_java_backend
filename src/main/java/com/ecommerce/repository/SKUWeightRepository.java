package com.ecommerce.repository;

import com.ecommerce.entity.SKUWeight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SKUWeightRepository extends JpaRepository<SKUWeight,Long> {
}
