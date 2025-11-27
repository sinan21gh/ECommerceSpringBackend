package com.sinans.ecommercebackend.Controller.Cart;

import com.sinans.ecommercebackend.Service.CartService;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ecommerce/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @PostMapping("/add/{id}")
    @PreAuthorize("hasRole('USER')")
    public CartDTO addToCart(@PathVariable Long id,
                             @RequestParam int quantity) {
        return cartService.addToCart(id, quantity);
    }

    @GetMapping
    @PreAuthorize("hasRole('USER')")
    public CartDTO getCart() {
        return cartService.getMyCart();
    }

    @DeleteMapping("/remove/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> deleteCart(@PathVariable @Min(1) Long id) {
        cartService.removeFromCart(id);
        return ResponseEntity.noContent().build();
    }
}

