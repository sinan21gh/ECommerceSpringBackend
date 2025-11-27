package com.sinans.ecommercebackend.Mappers;

import org.springframework.stereotype.Component;

@Component
public interface Mapper<T, U> {
    T DTOToEntity(U dto);
    U EntityToDTO(T entity);

}
