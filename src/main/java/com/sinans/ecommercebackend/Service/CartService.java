package com.sinans.ecommercebackend.Service;

import com.sinans.ecommercebackend.Controller.Cart.CartDTO;
import com.sinans.ecommercebackend.Controller.Cart.CartItemResponse;
import com.sinans.ecommercebackend.Persistence.Cart.CartEntity;
import com.sinans.ecommercebackend.Persistence.Cart.CartItemEntity;
import com.sinans.ecommercebackend.Persistence.Cart.CartRepository;
import com.sinans.ecommercebackend.Persistence.Product.ProductEntity;
import com.sinans.ecommercebackend.Persistence.Product.ProductRepository;
import com.sinans.ecommercebackend.Persistence.User.UserEntity;
import com.sinans.ecommercebackend.Persistence.User.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public CartDTO getMyCart() {
        CartEntity cart = getUserCart();
        return toDTO(cart);
    }

    public CartDTO addToCart(Long productId, int quantity) {

        if (quantity <= 0) {
            throw new RuntimeException("Quantity must be greater than 0");
        }

        CartEntity cart = getUserCart();
        ProductEntity product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        CartItemEntity existingItem = cart.getItems().stream()
                .filter(i -> i.getProduct().getId().equals(productId))
                .findFirst()
                .orElse(null);

        if (existingItem != null) {
            existingItem.setQuantity(existingItem.getQuantity() + quantity);
        } else {
            CartItemEntity newItem = new CartItemEntity();
            newItem.setCart(cart);
            newItem.setProduct(product);
            newItem.setPricePerItem(product.getPrice());
            newItem.setQuantity(quantity);
            cart.getItems().add(newItem);
        }

        updateTotal(cart);
        cartRepository.save(cart);

        return toDTO(cart);
    }

    public CartDTO removeFromCart(Long productId) {

        CartEntity cart = getUserCart();

        CartItemEntity item = cart.getItems().stream()
                .filter(i -> i.getProduct().getId().equals(productId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Product not in cart"));

        cart.getItems().remove(item);

        updateTotal(cart);
        cartRepository.save(cart);

        return toDTO(cart);
    }

    public void clearCart() {
        CartEntity cart = getUserCart();
        cart.getItems().clear();
        cart.setTotalPrice(0.0);
        cartRepository.save(cart);
    }

    private CartEntity getUserCart() {
        String username = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        UserEntity user = userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("User not found"));

        return cartRepository.findByUser_Username(username)
                .orElseGet(() -> {
                    CartEntity newCart = new CartEntity();
                    newCart.setUser(user);
                    newCart.setTotalPrice(0.0);
                    return cartRepository.save(newCart);
                });
    }

    private void updateTotal(CartEntity cart) {
        double total = cart.getItems().stream()
                .mapToDouble(i -> i.getPricePerItem() * i.getQuantity())
                .sum();
        cart.setTotalPrice(total);
    }

    private CartDTO toDTO(CartEntity cart) {
        List<CartItemResponse> items = cart.getItems().stream().map(i ->
                new CartItemResponse(
                        i.getProduct().getId(),
                        i.getProduct().getName(),
                        i.getPricePerItem(),
                        i.getQuantity()
                )
        ).toList();

        return new CartDTO(items, cart.getTotalPrice());
    }
}

