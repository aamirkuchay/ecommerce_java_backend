package com.ecommerce.service.impl;

import com.ecommerce.dto.OrderDTO;
import com.ecommerce.dto.OrderItemDTO;
import com.ecommerce.entity.*;
import com.ecommerce.exception.InsufficientStockException;
import com.ecommerce.exception.ResourceNotFoundException;
import com.ecommerce.repository.*;
import com.ecommerce.service.OrderService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductSKURepository productSKURepository;

    @Autowired
    private SKUSizeRepository skuSizeRepository;

    @Autowired
    private SKUWeightRepository skuWeightRepository;
    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private ProductRepository productRepository;




    @Transactional
    public OrderDTO createOrder(Long userId) {

        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart not found"));

        if (cart.getCartItems().isEmpty()) {
            throw new ResourceNotFoundException("Cart is empty");
        }

        List<Address> addressList = addressRepository.findByUserId(userId);
        if (addressList.isEmpty()) {
            throw new ResourceNotFoundException("No addresses found for user");
        }


        Address shippingAddress = addressList.stream()
                .filter(Address::isDefault)
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Default shipping address not found"));

        Address billingAddress = addressList.stream()
                .filter(Address::isDefault)
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Default billing address not found"));




        Order order = new Order();
        order.setUser(cart.getUser());
        order.setTotalAmount(calculateTotalAmount(cart));
        order.setStatus(ProductStatus.APPROVED);
        order.setCreatedAt(LocalDateTime.now());
        order.setUpdatedAt(LocalDateTime.now());
        order.setShippingAddress(shippingAddress);
        order.setBillingAddress(billingAddress);


        List<OrderItem> orderItems = new ArrayList<>();


        for (CartItem cartItem : cart.getCartItems()) {
            Product product = cartItem.getProduct();
            List<String> skus = product.getSkus().stream()
                    .map(ProductSKU::getSku)
                    .collect(Collectors.toList());

            ProductSKU productSKU = productSKURepository.findByProductAndSkuIn(product, skus)
                    .orElseThrow(() -> new ResourceNotFoundException("ProductSKU not found"));

            Size orderedSize = cartItem.getSize();
            Weight orderedWeight = cartItem.getWeight();

            product.reduceQuantity(Long.valueOf(cartItem.getQuantity()));
            productRepository.save(product);


            updateSKUQuantities(productSKU, cartItem.getQuantity(), orderedSize, orderedWeight);

            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setPrice(product.getBasePrice());
            orderItem.setProductSku(productSKU);
            orderItem.setQuantity(cartItem.getQuantity());
            orderItems.add(orderItem);


        }

        order.setItems(orderItems);


        order = orderRepository.save(order);


        cart.getCartItems().clear();
        cartRepository.save(cart);

        return mapToOrderDTO(order);
    }

    @Override
    public List<OrderDTO> getOrdersByUserId(Long userId) {
        List<Order> orders = orderRepository.findByUserId(userId);
        return orders.stream()
                .map(this::mapToOrderDTO)
                .collect(Collectors.toList());
    }








    private void updateSKUQuantities(ProductSKU productSKU, Integer quantity, Size orderedSize, Weight orderedWeight) {
        try {
            // Update SKU sizes
            for (SKUSize skuSize : productSKU.getSizes()) {

                if (skuSize.getSize().equals(orderedSize)) {
                    int newQuantity = skuSize.getQuantity() - quantity;
                    if (newQuantity <= 0) {
                        throw new ResourceNotFoundException("Not enough quantity available for the selected size");
                    }
                    skuSize.setQuantity(newQuantity);
                    skuSizeRepository.save(skuSize);

                    break;
            }}

            // Update SKU weights
            for (SKUWeight skuWeight : productSKU.getWeights()) {
                if (skuWeight.getWeight().equals(orderedWeight)) {
                    skuWeight.reduceQuantity(quantity);
                    skuWeightRepository.save(skuWeight);
                    break;
                }
            }
        } catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException("Unable to find the requested size or weight for the product SKU.", e);
        }
    }




    private BigDecimal calculateTotalAmount(Cart cart) {
        return cart.getCartItems().stream()
                .map(cartItem -> cartItem.getProduct().getBasePrice().multiply(BigDecimal.valueOf(cartItem.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public OrderDTO mapToOrderDTO(Order order) {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setId(order.getId());
        orderDTO.setUserId(order.getUser().getId());
        orderDTO.setTotalAmount(order.getTotalAmount());
        orderDTO.setStatus(order.getStatus().name());
        orderDTO.setCreatedAt(order.getCreatedAt());
        orderDTO.setUpdatedAt(order.getUpdatedAt());

        List<OrderItemDTO> itemDTOs = order.getItems().stream()
                .map(orderItem -> {
                    OrderItemDTO itemDTO = new OrderItemDTO();
                    itemDTO.setId(orderItem.getId());
                    itemDTO.setProductSkuId(orderItem.getProductSku().getId());
                    itemDTO.setQuantity(orderItem.getQuantity());
                    return itemDTO;
                })
                .collect(Collectors.toList());

        orderDTO.setItems(itemDTOs);
        return orderDTO;
    }











}
