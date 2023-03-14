package az.enjooy.service.implementation;

import az.enjooy.model.entity.User;
import az.enjooy.repository.UserRepository;
import az.enjooy.model.entity.Permission;
import az.enjooy.model.entity.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class AppUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) {
        User user = userRepository.findByUsername(username);
        if(user != null && user.isActive()) {
            return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),getGrantedAuthorities(user));
        }else {
            throw new UsernameNotFoundException(String.format("The username %s doesn't exist", username));
        }

    }

    public List<GrantedAuthority> getGrantedAuthorities(User user) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (final Role role : user.getRoles()) {
            GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + role.getName());
            authorities.add(authority);
            for (Permission permission : role.getPermissions()){
                authorities.add(new SimpleGrantedAuthority(permission.getName()));
            }
        }
        return authorities;
    }
}
