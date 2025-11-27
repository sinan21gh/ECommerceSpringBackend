package com.sinans.ecommercebackend.Mappers.DTOMappers;

import com.sinans.ecommercebackend.Controller.Users.UserDTO;
import com.sinans.ecommercebackend.Mappers.Mapper;
import com.sinans.ecommercebackend.Persistence.User.UserEntity;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserMapper implements Mapper<UserEntity, UserDTO> {

    private final ModelMapper modelMapper;

    @Override
    public UserEntity DTOToEntity(UserDTO dto) {
        return modelMapper.map(dto, UserEntity.class);
    }

    @Override
    public UserDTO EntityToDTO(UserEntity entity) {
        return modelMapper.map(entity, UserDTO.class);
    }
}
