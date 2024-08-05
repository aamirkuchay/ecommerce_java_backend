package com.ecommerce.controller;

import com.ecommerce.entity.Size;
import com.ecommerce.entity.Weight;
import com.ecommerce.exception.ResourceNotFoundException;
import com.ecommerce.service.WeightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/weights")
public class WeightController {

    @Autowired
    private WeightService weightService;



    @PostMapping
    public Weight createWeight(@RequestBody Weight weight) {
        return weightService.saveSize(weight);
    }


    @GetMapping("/{id}")
    public ResponseEntity<Weight> getWeightById(@PathVariable Long id) {
        Optional<Weight> weight = weightService.getWeightById(id);
        return weight.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }


    @PutMapping("/{id}")
    public ResponseEntity<Weight> updateWeight(@PathVariable Long id, @RequestBody Weight weight) {
        try {
            Weight updatedWeight = weightService.updateWeight(id, weight);
            return ResponseEntity.ok(updatedWeight);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteWeight(@PathVariable Long id) {
        try {
            weightService.deleteWeight(id);
            return ResponseEntity.noContent().build();
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public List<Weight> getAllWeights() {
        return weightService.getAllWeights();
    }


}
