package com.sinans.ecommercebackend.Controller.Cart;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartItemResponse {
    private Long productId;
    private String name;
    private Double pricePerItem;
    private int quantity;
}

