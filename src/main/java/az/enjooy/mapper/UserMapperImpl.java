package az.enjooy.mapper;

import az.enjooy.dto.RoleDTO;
import az.enjooy.model.entity.Role;
import az.enjooy.model.entity.User;
import az.enjooy.dto.authentication.UserDTO;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public User fromDTO(UserDTO userDTO) {
        if ( userDTO == null ) {
            return null;
        }

        User user = new User();

        user.setId( userDTO.getId() );
        user.setUsername( userDTO.getUsername() );
        user.setPassword( userDTO.getPassword() );
        user.setFirstName( userDTO.getFirstName() );
        user.setLastName( userDTO.getLastName() );
        user.setEmail( userDTO.getEmail() );
        user.setRoles( roleDTOListToRoleSet( userDTO.getRoles() ) );
        user.setActive( userDTO.isActive() );

        return user;
    }

    @Override
    public Role fromDto(RoleDTO roleDTO) {
        if ( roleDTO == null ) {
            return null;
        }

        Role role = new Role();

        return role;
    }

    @Override
    public RoleDTO fromEntity(Role role) {
        if ( role == null ) {
            return null;
        }

        RoleDTO roleDTO = new RoleDTO();

        return roleDTO;
    }

    @Override
    public UserDTO fromEntity(User user) {
        if ( user == null ) {
            return null;
        }

        UserDTO userDTO = new UserDTO();

        userDTO.setPhoto( user.getPhotoUrl() );
        userDTO.setId( user.getId() );
        userDTO.setUsername( user.getUsername() );
        userDTO.setFirstName( user.getFirstName() );
        userDTO.setLastName( user.getLastName() );
        userDTO.setEmail( user.getEmail() );
        userDTO.setPassword( user.getPassword() );
        userDTO.setRoles( roleSetToRoleDTOList( user.getRoles() ) );
        userDTO.setActive( user.isActive() );

        return userDTO;
    }

    protected Set<Role> roleDTOListToRoleSet(List<RoleDTO> list) {
        if ( list == null ) {
            return null;
        }

        Set<Role> set = new HashSet<Role>( Math.max( (int) ( list.size() / .75f ) + 1, 16 ) );
        for ( RoleDTO roleDTO : list ) {
            set.add( fromDto( roleDTO ) );
        }

        return set;
    }

    protected List<RoleDTO> roleSetToRoleDTOList(Set<Role> set) {
        if ( set == null ) {
            return null;
        }

        List<RoleDTO> list = new ArrayList<RoleDTO>( set.size() );
        for ( Role role : set ) {
            list.add( fromEntity( role ) );
        }

        return list;
    }
}
