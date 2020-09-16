package com.kibrit.authentication.mapper;

import com.kibrit.authentication.dto.UserDTO;
import com.kibrit.authentication.dto.UserUpdateDTO;
import com.kibrit.authentication.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserMapper MAPPER = Mappers.getMapper(UserMapper.class);
    User mapFromUserDTO(UserDTO userDTO);
    User mapFromUserUpdateDTO(UserUpdateDTO userUpdateDTO);
}
