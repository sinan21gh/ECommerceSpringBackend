package com.sinans.ecommercebackend.Mappers.DTOMappers;

import com.sinans.ecommercebackend.Controller.Orders.OrderItemDTO;
import com.sinans.ecommercebackend.Controller.Product.ProductDTO;
import com.sinans.ecommercebackend.Mappers.Mapper;
import com.sinans.ecommercebackend.Persistence.Orders.OrderEntity;
import com.sinans.ecommercebackend.Persistence.Orders.OrderItemEntity;
import com.sinans.ecommercebackend.Persistence.Product.ProductEntity;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderItemsMapper implements Mapper<OrderItemEntity, OrderItemDTO> {

    private final ModelMapper mapper;

    @Override
    public OrderItemDTO EntityToDTO(OrderItemEntity entity) {
        return mapper.map(entity, OrderItemDTO.class);
    }

    @Override
    public OrderItemEntity DTOToEntity(OrderItemDTO dto) {
        return mapper.map(dto, OrderItemEntity.class);
    }
}
