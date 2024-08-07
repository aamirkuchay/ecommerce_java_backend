package com.ecommerce.service;

import com.ecommerce.entity.Address;

import java.util.List;

public interface AddressService {
    Address createAddress(Long userId, Address address);

    Address getAddressById(Long id);

    List<Address> getAddressByUserId(Long userId);
}
