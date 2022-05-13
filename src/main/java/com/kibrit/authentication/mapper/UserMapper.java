package com.kibrit.authentication.mapper;

import com.kibrit.authentication.dto.RoleDTO;
import com.kibrit.authentication.dto.UserDTO;
import com.kibrit.authentication.model.Role;
import com.kibrit.authentication.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;


@Mapper(componentModel = "spring")
public interface UserMapper {
    UserMapper MAPPER = Mappers.getMapper(UserMapper.class);

    User fromDTO(UserDTO userDTO);

    Role fromDto(RoleDTO roleDTO);

    RoleDTO fromEntity(Role role);

    @Mapping(target="photo", source="photoUrl")
    UserDTO fromEntity(User user);



}