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
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
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




    @Transactional
    public OrderDTO createOrder(Long userId) {
        // Fetch or create cart
        Cart cart = cartRepository.findByUserId(userId).orElseThrow(() -> new ResourceNotFoundException("Cart not found"));

        if (cart.getCartItems().isEmpty()) {
            throw new ResourceNotFoundException("Cart is empty");
        }

        // Create order
        Order order = new Order();
        order.setUser(cart.getUser());
        order.setTotalAmount(calculateTotalAmount(cart));
        order.setStatus(ProductStatus.AVAILABLE);
        order.setCreatedAt(LocalDateTime.now());
        order.setUpdatedAt(LocalDateTime.now());

        // Process each cart item
        for (CartItem cartItem : cart.getCartItems()) {
            ProductSKU productSKU = productSKURepository.findByProductAndSkus(cartItem.getProduct(), cartItem.getProduct().getSkus())
                    .orElseThrow(() -> new ResourceNotFoundException("ProductSKU not found"));

            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setProductSku(productSKU);
            orderItem.setQuantity(cartItem.getQuantity());

            // Update SKU size and weight quantities
            updateSKUQuantities(productSKU, cartItem.getQuantity());
        }

        // Save order
        orderRepository.save(order);

        // Clear cart
        cart.getCartItems().clear();
        cartRepository.save(cart);

        return mapToOrderDTO(order);
    }

    private BigDecimal calculateTotalAmount(Cart cart) {
        return cart.getCartItems().stream()
                .map(cartItem -> cartItem.getProduct().getBasePrice().multiply(BigDecimal.valueOf(cartItem.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private void updateSKUQuantities(ProductSKU productSKU, Integer quantity) {
        for (SKUSize skuSize : productSKU.getSizes()) {
            if (skuSize.getSize().equals(productSKU.getSizes())) {
                skuSize.setQuantity(skuSize.getQuantity() - quantity);
                skuSizeRepository.save(skuSize);
                break;
            }
        }

        for (SKUWeight skuWeight : productSKU.getWeights()) {
            if (skuWeight.getWeight().equals(productSKU.getWeights())) {
                skuWeight.setQuantity(skuWeight.getQuantity() - quantity);
                skuWeightRepository.save(skuWeight);
                break;
            }
        }
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











//    @Override
//    public Order createOrder(Order order) {
//
//        User user = userRepository.findById(order.getUser().getId())
//                .orElseThrow(() -> new IllegalArgumentException("Invalid user ID: " + order.getUser().getId()));
//
//        // Fetch the role and assign it to the user if not already set
//        if (user.getRole() == null) {
//            Role userRole = roleRepository.findByName("ROLE_USER")
//                    .orElseThrow(() -> new IllegalArgumentException("Role not found: ROLE_USER"));
//            user.setRole(userRole);
//        }
//        order.setUser(user);
//        // Ensure the shipping and billing addresses are managed entities
//        Address managedShippingAddress = addressRepository.findById(order.getShippingAddress().getId())
//                .orElseThrow(() -> new IllegalArgumentException("Invalid shipping address ID: " + order.getShippingAddress().getId()));
//        order.setShippingAddress(managedShippingAddress);
//
//        Address managedBillingAddress = addressRepository.findById(order.getBillingAddress().getId())
//                .orElseThrow(() -> new IllegalArgumentException("Invalid billing address ID: " + order.getBillingAddress().getId()));
//        order.setBillingAddress(managedBillingAddress);
//
//
//
//
//        for (OrderItem orderItem : order.getItems()) {
//            ProductSKU productSKU = productSKURepository.findById(orderItem.getProductSku().getId())
//                    .orElseThrow(() -> new IllegalArgumentException("Invalid ProductSKU ID: " + orderItem.getProductSku().getId()));
//
//            orderItem.setProductSku(productSKU);
//            orderItem.setOrder(order); // Set the order reference in each OrderItem
//            updateSKUQuantities(productSKU, orderItem);
//        }
//
//        return orderRepository.save(order);
//    }
//
//    private void updateSKUQuantities(ProductSKU productSKU, OrderItem orderItem) {
//        if (productSKU.getSizes() != null) {
//            for (SKUSize skuSize : productSKU.getSizes()) {
//                if (skuSize.getSize().equals(orderItem.getSize())) {
//                    int newQuantity = skuSize.getQuantity() - orderItem.getQuantity();
//                    if (newQuantity < 0) {
//                        throw new InsufficientStockException("Not enough stock for size: " + skuSize.getSize().getName());
//                    }
//                    skuSize.setQuantity(newQuantity);
//                    skuSizeRepository.save(skuSize);
//                    System.err.println("Updated SKUSize ID: " + skuSize.getId() + ", New Quantity: " + newQuantity);
//                }
//            }
//        }
//
//        if (productSKU.getWeights() != null) {
//            for (SKUWeight skuWeight : productSKU.getWeights()) {
//                if (skuWeight.getWeight().equals(orderItem.getWeight())) {
//                    int newQuantity = skuWeight.getQuantity() - orderItem.getQuantity();
//                    if (newQuantity < 0) {
//                        throw new InsufficientStockException("Not enough stock for weight: " + skuWeight.getWeight().getValue());
//                    }
//                    skuWeight.setQuantity(newQuantity);
//                    skuWeightRepository.save(skuWeight);
//                }
//            }
//        }
//
//        productSKURepository.save(productSKU);
//    }
}
