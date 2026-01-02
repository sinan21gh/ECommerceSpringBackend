package com.sinans.ecommercebackend.Controller.Orders;

import com.sinans.ecommercebackend.Persistence.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateStatus {
    private OrderStatus status;
}
