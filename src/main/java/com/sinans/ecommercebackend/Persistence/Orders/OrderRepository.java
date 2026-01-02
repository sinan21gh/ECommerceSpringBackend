package com.sinans.ecommercebackend.Persistence.Orders;

import com.sinans.ecommercebackend.Persistence.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

public interface OrderRepository extends JpaRepository<OrderEntity, Long> {
    List<OrderEntity> findByUser_Username(String username);
    //this is just an extra method i created so it allows admins to search for a specific user and status which makes updating the status faster
    List<OrderEntity> findByUser_UsernameAndStatus(String username, OrderStatus status);
}
