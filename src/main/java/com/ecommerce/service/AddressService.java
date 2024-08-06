package com.ecommerce.service;

import com.ecommerce.entity.Address;

public interface AddressService {
    Address createAddress(Long userId, Address address);

    Address getAddressById(Long id);
}
