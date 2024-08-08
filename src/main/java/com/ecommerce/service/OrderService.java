package com.ecommerce.service;

import com.ecommerce.dto.OrderDTO;
import com.ecommerce.entity.Order;

public interface OrderService {
    OrderDTO createOrder(Long userId);

}
