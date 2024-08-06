package com.ecommerce.service;

import com.ecommerce.dto.CartDTO;
import com.ecommerce.dto.CartItemDTO;

public interface CartService {
    CartDTO addItemToCart(Long userId, CartItemDTO cartItemDTO);

    CartDTO getCart(Long userId);
}
