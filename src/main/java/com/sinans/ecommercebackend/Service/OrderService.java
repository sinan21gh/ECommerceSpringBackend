package com.sinans.ecommercebackend.Service;

import com.sinans.ecommercebackend.Controller.Orders.OrderDTO;
import com.sinans.ecommercebackend.Controller.Orders.OrderItemDTO;
import com.sinans.ecommercebackend.Mappers.DTOMappers.OrderItemsMapper;
import com.sinans.ecommercebackend.Mappers.DTOMappers.OrdersMapper;
import com.sinans.ecommercebackend.Persistence.Cart.CartEntity;
import com.sinans.ecommercebackend.Persistence.Cart.CartItemEntity;
import com.sinans.ecommercebackend.Persistence.Cart.CartRepository;
import com.sinans.ecommercebackend.Persistence.OrderStatus;
import com.sinans.ecommercebackend.Persistence.Orders.OrderEntity;
import com.sinans.ecommercebackend.Persistence.Orders.OrderItemEntity;
import com.sinans.ecommercebackend.Persistence.Orders.OrderRepository;
import com.sinans.ecommercebackend.Persistence.Product.ProductEntity;
import com.sinans.ecommercebackend.Persistence.Product.ProductRepository;
import jakarta.annotation.PreDestroy;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final OrderItemsMapper orderItemsMapper;
    private final OrdersMapper orderMapper;


    @Transactional
    public OrderDTO placeOrder() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        CartEntity cart;
        if (cartRepository.findByUser_Username(username).isPresent()) {
            cart = cartRepository.findByUser_Username(username).get();
        } else {
            throw new RuntimeException("Cart not found");
        }

        if (cart.getItems() == null || cart.getItems().isEmpty()) {
            throw new RuntimeException("Cart is empty");
        }

        OrderEntity order = new OrderEntity();
        order.setUser(cart.getUser());
        order.setStatus(OrderStatus.PLACED);
        order.setCreationDate(Instant.now());
        order.setItems(cart.getItems().stream().map(item -> new OrderItemEntity(null, order, item.getProduct(), item.getQuantity(), item.getQuantity() * item.getProduct().getPrice())).toList());
        double price = cart.getItems().stream().mapToDouble(s -> s.getPricePerItem() * s.getQuantity()).sum();
        order.setTotalPrice(price);
        orderRepository.save(order);
        List<OrderItemDTO> orders = order.getItems().stream().map(
                item -> new OrderItemDTO(item.getProduct().getId(), item.getProduct().getName(), item.getProduct().getPrice() * item.getQuantity(), item.getQuantity())).toList();
        cart.getItems().clear();
        cartRepository.save(cart);

        return new OrderDTO(order.getId(), orders, order.getStatus().toString(), price, order.getCreationDate());
    }

    public List<OrderDTO> getMyOrders() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        List<OrderEntity> orderEntities = orderRepository.findByUser_Username(username);
        return helpReturn(orderEntities);
    }

    private List<OrderDTO> helpReturn(List<OrderEntity> orderEntities) {
        return orderEntities.stream().map(order -> {
            List<OrderItemDTO> itemDTOs = order.getItems().stream()
                    .map(item -> new OrderItemDTO(
                            item.getProduct().getId(),
                            item.getProduct().getName(),
                            item.getProduct().getPrice() * item.getQuantity(),
                            item.getQuantity()
                    )).toList();

            return new OrderDTO(
                    order.getId(),
                    itemDTOs,
                    order.getStatus().toString(),
                    order.getTotalPrice(),
                    order.getCreationDate()
            );
        }).toList();
    }

    @PreAuthorize("hasRole('ADMIN')")
    public List<OrderDTO> getAllOrders() {
        List<OrderEntity> orderEntities = orderRepository.findAll();
        return helpReturn(orderEntities);
    }

    public OrderDTO updateOrderStatus(Long id, OrderStatus status) {
        OrderEntity order =  orderRepository.findById(id).orElseThrow(() -> new RuntimeException("Order not found"));

        if (status == OrderStatus.CANCELLED) {
           order.getItems().forEach(s -> productRepository.findById(s.getProduct().getId()).ifPresent(i -> {
               i.setQuantity(i.getQuantity() + s.getQuantity());
               productRepository.save(i);
           }
           ));
        }
        order.setStatus(status);
        orderRepository.save(order);
        return orderMapper.EntityToDTO(order);
    }
}

