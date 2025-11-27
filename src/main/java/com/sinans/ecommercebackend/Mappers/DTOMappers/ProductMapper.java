package com.sinans.ecommercebackend.Mappers.DTOMappers;

import com.sinans.ecommercebackend.Controller.Product.ProductDTO;
import com.sinans.ecommercebackend.Mappers.Mapper;
import com.sinans.ecommercebackend.Persistence.Product.ProductEntity;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProductMapper implements Mapper<ProductEntity, ProductDTO> {

    private final ModelMapper mapper;

    @Override
    public ProductDTO EntityToDTO(ProductEntity entity) {
        return mapper.map(entity, ProductDTO.class);
    }

    @Override
    public ProductEntity DTOToEntity(ProductDTO dto) {
        return mapper.map(dto, ProductEntity.class);
    }
}

