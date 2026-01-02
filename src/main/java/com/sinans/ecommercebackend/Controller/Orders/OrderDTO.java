package com.sinans.ecommercebackend.Controller.Orders;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO {
    private Long id;
    private List<OrderItemDTO> items;
    private String status;
    private Double price;
    private Instant creationDate;
}
