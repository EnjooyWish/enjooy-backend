package com.kibrit.authentication.mapper;

import com.kibrit.authentication.dto.UserDTO;
import com.kibrit.authentication.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;


@Mapper(componentModel = "spring")
public interface UserMapper {
    UserMapper MAPPER = Mappers.getMapper(UserMapper.class);

    User fromDTO(UserDTO userDTO);


    @Mapping(target="photo", source="user.photoUrl")
    UserDTO fromEntity(User user);
}