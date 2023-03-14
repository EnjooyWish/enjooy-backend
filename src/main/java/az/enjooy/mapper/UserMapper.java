package az.enjooy.mapper;

import az.enjooy.model.entity.User;
import az.enjooy.dto.RoleDTO;
import az.enjooy.dto.authentication.UserDTO;
import az.enjooy.model.entity.Role;
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