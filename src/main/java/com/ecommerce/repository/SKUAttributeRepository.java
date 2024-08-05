package com.ecommerce.repository;

import com.ecommerce.entity.SKUAttribute;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SKUAttributeRepository extends JpaRepository<SKUAttribute,Long> {
}
