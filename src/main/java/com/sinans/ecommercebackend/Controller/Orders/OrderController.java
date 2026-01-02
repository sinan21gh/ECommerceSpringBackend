package com.sinans.ecommercebackend.Controller.Orders;

import com.sinans.ecommercebackend.Persistence.OrderStatus;
import com.sinans.ecommercebackend.Service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "ecommerce/orders/")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping(path = "place")
    public OrderDTO placeOrder() {
        return orderService.placeOrder();
    }

    @GetMapping(path = "my")
    public List<OrderDTO> getMyOrders() {
        return orderService.getMyOrders();
    }
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(path = "getorders")
    public List<OrderDTO> getOrders() {
        return orderService.getAllOrders();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping(path = "updateorders/{id}")
    public OrderDTO updateOrders(@PathVariable Long id, @RequestBody UpdateStatus orderStatus) {
        return orderService.updateOrderStatus(id, orderStatus.getStatus());
    }
}
