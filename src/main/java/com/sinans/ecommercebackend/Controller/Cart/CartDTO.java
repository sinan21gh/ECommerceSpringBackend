package com.sinans.ecommercebackend.Controller.Cart;

import com.sinans.ecommercebackend.Controller.Users.UserDTO;
import com.sinans.ecommercebackend.Persistence.Cart.CartItemEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartDTO {
    private List<CartItemResponse> items;
    private Double totalPrice;
}

