package com.sinans.ecommercebackend.Mappers.DTOMappers;

import com.sinans.ecommercebackend.Controller.Orders.OrderDTO;
import com.sinans.ecommercebackend.Controller.Orders.OrderItemDTO;
import com.sinans.ecommercebackend.Mappers.Mapper;
import com.sinans.ecommercebackend.Persistence.Orders.OrderEntity;
import com.sinans.ecommercebackend.Persistence.Orders.OrderItemEntity;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrdersMapper implements Mapper<OrderEntity, OrderDTO> {

    private final ModelMapper mapper;

    @Override
    public OrderDTO EntityToDTO(OrderEntity entity) {
        return mapper.map(entity, OrderDTO.class);
    }

    @Override
    public OrderEntity DTOToEntity(OrderDTO dto) {
        return mapper.map(dto, OrderEntity.class);
    }
}