package com.ecommerce.service.impl;

import com.ecommerce.dto.CartDTO;
import com.ecommerce.dto.CartItemDTO;
import com.ecommerce.dto.UserDTO;
import com.ecommerce.entity.*;
import com.ecommerce.exception.ResourceNotFoundException;
import com.ecommerce.repository.*;
import com.ecommerce.service.CartService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CartServiceImpl implements CartService {

    private final ProductRepository productRepository;
    private final CartItemRepository cartItemRepository;
    private final CartRepository cartRepository;
    private final UserRepository userRepository;

    private final SizeRepository sizeRepository;
    private final WeightRepository weightRepository;

    @Override
    public CartDTO addItemToCart(Long userId, CartItemDTO cartItemDTO) {

        Cart cart = cartRepository.findByUserId(userId).orElseGet(() -> createCart(userId));

        Product product = productRepository.findById(cartItemDTO.getProductId())
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));


        Size size = cartItemDTO.getSizeId() != null ? sizeRepository.findById(cartItemDTO.getSizeId()).orElse(null) : null;
        Weight weight = cartItemDTO.getWeightId() != null ? weightRepository.findById(cartItemDTO.getWeightId()).orElse(null) : null;

        CartItem cartItem = cartItemRepository.findByCartAndProductAndSizeAndWeight(cart, product, size, weight)
                .orElse(new CartItem());

        cartItem.setCart(cart);
        cartItem.setProduct(product);
        cartItem.setQuantity(cartItemDTO.getQuantity());
        cartItem.setSize(size);
        cartItem.setWeight(weight);
        cartItemRepository.save(cartItem);
        cart.getCartItems().add(cartItem);
        cartRepository.save(cart);
        return mapToCartDTO(cart);
    }

    @Override
    public CartDTO getCart(Long userId) {
        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart not found for user " + userId));
        return mapToCartDTO(cart);
    }


    private Cart createCart(Long userId) {
        Cart newCart = new Cart();
        // Set user and other properties
        newCart.setUser(userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found")));
        return cartRepository.save(newCart);
    }

    private CartDTO mapToCartDTO(Cart cart) {
        CartDTO cartDTO = new CartDTO();
        cartDTO.setId(cart.getId());
        cartDTO.setUser(new UserDTO(cart.getUser().getId(), cart.getUser().getUsername()));

        List<CartItemDTO> cartItemDTOs = cart.getCartItems().stream().map(cartItem -> {
            CartItemDTO itemDTO = new CartItemDTO();
            itemDTO.setProductId(cartItem.getProduct().getId());
            itemDTO.setQuantity(cartItem.getQuantity());
            if (cartItem.getSize() != null) {
                itemDTO.setSizeId(cartItem.getSize().getId());
            } else {
                itemDTO.setSizeId(null);
            }

            if (cartItem.getWeight() != null) {
                itemDTO.setWeightId(cartItem.getWeight().getId());
            } else {
                itemDTO.setWeightId(null);
            }
            return itemDTO;
        }).collect(Collectors.toList());

        cartDTO.setCartItems(cartItemDTOs);
        return cartDTO;
    }
}
