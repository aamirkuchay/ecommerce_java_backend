package com.ecommerce.service.impl;


import com.ecommerce.entity.Size;
import com.ecommerce.entity.Weight;
import com.ecommerce.exception.ResourceNotFoundException;
import com.ecommerce.repository.WeightRepository;
import com.ecommerce.service.WeightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WeightServiceImpl implements WeightService {

    @Autowired

    private WeightRepository weightRepository;

    @Override
    public Weight saveSize(Weight weight) {
        return weightRepository.save(weight);
    }

    @Override
    public Optional<Weight> getWeightById(Long id) {
        return weightRepository.findById(id);
    }

    @Override
    public Weight updateWeight(Long id, Weight weight) {
        Weight weight1 = weightRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Weight  not found with id " + id));
         weight1.setValue(weight.getValue());
        return weightRepository.save(weight1);
    }

    @Override
    public void deleteWeight(Long id) {
        Weight weight = weightRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Weight  not found with id " + id));
        weightRepository.delete(weight);
    }


    @Override
    public Page<Weight> getAllWeights(int page) {
        Pageable pageable = PageRequest.of(page - 1, 10);
        return weightRepository.findAll(pageable);
    }
}
