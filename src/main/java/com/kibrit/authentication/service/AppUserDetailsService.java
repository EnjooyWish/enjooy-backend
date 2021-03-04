package com.kibrit.authentication.service;

import com.kibrit.authentication.model.Permission;
import com.kibrit.authentication.model.Role;
import com.kibrit.authentication.model.User;
import com.kibrit.authentication.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class AppUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public AppUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        User user = userRepository.findByUsername(username);
        if(user != null && user.isActive()) {
            List<GrantedAuthority> authorities = new ArrayList<>();
//            user.getRoles().forEach(role ->
//                    authorities.add(new SimpleGrantedAuthority(role.getRoleName())));
            return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authorities);
        }else {
            throw new UsernameNotFoundException(String.format("The username %s doesn't exist", username));
        }

    }

    private List<GrantedAuthority> getGrantedAuthorities(User user) {
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        for (final Role role : user.getRoles()) {
            GrantedAuthority authority = new SimpleGrantedAuthority(role.getName());
            authorities.add(authority);
            for (Permission permission : role.getPermissions()){
                authorities.add(new SimpleGrantedAuthority(permission.getName()));
            }
        }
        return authorities;
    }
}
