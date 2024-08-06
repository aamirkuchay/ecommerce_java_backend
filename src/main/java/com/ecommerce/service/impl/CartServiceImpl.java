package com.ecommerce.service.impl;

import com.ecommerce.dto.CartDTO;
import com.ecommerce.dto.CartItemDTO;
import com.ecommerce.dto.UserDTO;
import com.ecommerce.entity.Cart;
import com.ecommerce.entity.CartItem;
import com.ecommerce.entity.Product;
import com.ecommerce.exception.ResourceNotFoundException;
import com.ecommerce.repository.CartItemRepository;
import com.ecommerce.repository.CartRepository;
import com.ecommerce.repository.ProductRepository;
import com.ecommerce.repository.UserRepository;
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

    @Override
    public CartDTO addItemToCart(Long userId, CartItemDTO cartItemDTO) {
        // Fetch or create cart
        Cart cart = cartRepository.findByUserId(userId).orElseGet(() -> createCart(userId));

        // Fetch product
        Product product = productRepository.findById(cartItemDTO.getProductId())
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        // Create or update cart item
        CartItem cartItem = cartItemRepository.findByCartAndProduct(cart, product)
                .orElse(new CartItem());
        cartItem.setCart(cart);
        cartItem.setProduct(product);
        cartItem.setQuantity(cartItemDTO.getQuantity());

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
            return itemDTO;
        }).collect(Collectors.toList());

        cartDTO.setCartItems(cartItemDTOs);
        return cartDTO;
    }
}
