package com.ecommerce.service;

import com.ecommerce.entity.Weight;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface WeightService {
    Weight saveSize(Weight weight);

    Optional<Weight> getWeightById(Long id);

    Weight updateWeight(Long id, Weight weight);

    void deleteWeight(Long id);

    Page<Weight> getAllWeights(int page);
}
