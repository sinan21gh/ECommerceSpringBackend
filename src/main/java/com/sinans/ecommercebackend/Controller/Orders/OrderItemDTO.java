package com.sinans.ecommercebackend.Controller.Orders;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemDTO {
    private Long productID;
    private String productName;
    private Double totalProductPrice;
    private Integer quantity;
}
