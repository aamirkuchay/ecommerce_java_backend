package com.ecommerce.controller;


import com.ecommerce.entity.Address;
import com.ecommerce.exception.ResourceNotFoundException;
import com.ecommerce.service.AddressService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/address")
@AllArgsConstructor
public class AddressController {

    private final AddressService addressService;


    @PostMapping
    public ResponseEntity<Address> createAddress(@RequestParam("userId") Long userId, @RequestBody Address address) {
        try {
            Address createdAddress = addressService.createAddress(userId, address);
            return new ResponseEntity<>(createdAddress, HttpStatus.CREATED);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @GetMapping("/{id}")
    public ResponseEntity<Address> getAddress(@PathVariable Long id) {
        Address address = addressService.getAddressById(id);
        if (address != null) {
            return new ResponseEntity<>(address, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @GetMapping("/{userId}/user-address")
    public ResponseEntity<List<Address>> getAddressByUser(@PathVariable("userId") Long userId) {
        List<Address> address = addressService.getAddressByUserId(userId);
        if (address != null) {
            return new ResponseEntity<>(address, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
