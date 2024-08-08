package com.ecommerce.service;

import com.ecommerce.dto.OrderDTO;
import com.ecommerce.entity.Order;

import java.util.List;

public interface OrderService {
    OrderDTO createOrder(Long userId);

    List<OrderDTO> getOrdersByUserId(Long userId);
}
