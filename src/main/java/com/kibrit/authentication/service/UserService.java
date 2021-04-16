package com.kibrit.authentication.service;

import com.kibrit.authentication.dto.RoleDTO;
import com.kibrit.authentication.dto.UserDTO;
import com.kibrit.authentication.dto.UserPasswordDTO;
import com.kibrit.authentication.exception.InvalidOldPasswordException;
import com.kibrit.authentication.exception.UsernameAlreadyExistsException;
import com.kibrit.authentication.model.Permission;
import com.kibrit.authentication.model.Role;
import com.kibrit.authentication.model.User;
import com.kibrit.authentication.repository.RoleRepository;
import com.kibrit.authentication.repository.UserRepository;
import com.kibrit.authentication.service.abstraction.RoleService;
import com.kibrit.authentication.util.GenericResponse;
import com.kibrit.authentication.dto.AdminPasswordDTO;
import com.kibrit.authentication.exception.ResourceNotFoundException;
import com.kibrit.authentication.model.LightUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Value("${default.password}")
    private String defaultPassword;

    final UserRepository userRepository;

    @Autowired
     RoleRepository roleRepository;

    @Autowired
    RoleService roleService;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public User save(UserDTO userDTO){
//        Set<Role> newRoles = new LinkedHashSet();
//        Set<Role> roles = new LinkedHashSet();
        User user;
        if(userDTO.getId() != null){
            user = findById(userDTO.getId());
        }else {
            user = new User();
            setDefaultPassword(user);
        }
        checkUserExistence(userDTO.getUsername(),userDTO.getId());
        user.setUsername(userDTO.getUsername());
        user.setPhoto(userDTO.getPhoto());
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setEmail(userDTO.getEmail());
//        for ( Role role : new ArrayList<>(user.getRoles())) {
//              role.removeUser(user);
//              roles.add(role);
//        }
//        roleRepository.saveAll(roles);
//        for (RoleDTO roleDTO : userDTO.getRoles()){
//            Role role = roleService.findById(roleDTO.getId());
//            newRoles.add(role);
//        }
//        newRoles.forEach(newRole -> newRole.addUser(user));
//        user.setRoles(newRoles);
        return userRepository.save(user);
    }

    public void resetPassword(Long id){
        User user = findById(id);
        setDefaultPassword(user);
        userRepository.save(user);
    }

    public void setDefaultPassword(User user){
        user.setPassword(bCryptPasswordEncoder.encode(defaultPassword));
    }

    public User activateOrDeactivate(Long id, boolean isActive){
        User user = findById(id);
        user.setActive(isActive);
        return userRepository.save(user);
    }

    public User findById(Long id){
       return   userRepository.findById(id)
                .orElseThrow(() ->
                new ResourceNotFoundException("User not found with id = " + id));
    }

    public User  findByUsername(String username){
        return userRepository.findByUsername(username);
    }

    public boolean isValidPassword(String password, String encryptedPassword){
        return BCrypt.checkpw(password,encryptedPassword);
    }

    public void changePasswordByUser(User user, UserPasswordDTO passwordDto){
        if (!isValidPassword(passwordDto.getOldPassword(),user.getPassword())) {
            throw new InvalidOldPasswordException("Invalid old password");
        }else {
            user.setPassword(new BCryptPasswordEncoder().encode(passwordDto.getNewPassword()));
            userRepository.save(user);
        }
    }

    public GenericResponse changePasswordByAdmin(User user, AdminPasswordDTO adminPasswordDTO){
        user.setPassword(new BCryptPasswordEncoder().encode(adminPasswordDTO.getNewPassword()));
        userRepository.save(user);
        return new GenericResponse("success", "Password changed successfully");
    }

    public void deleteUser(Long id){
        userRepository.deleteById(id);
    }

    public void checkUserExistence(String username, Long id){
        User user = findByUsername(username);
        User existingUser = null;
        if(id != null) {
            existingUser = findById(id);
        }
        if((id == null && user != null) ||
                (existingUser != null && user != null && user.getId() != existingUser.getId())){
            throw new UsernameAlreadyExistsException("User with username " + username + " already exists");
        }
    }
    public boolean userExists(String username){
        User user = null;
        if(username != null) {
            user = userRepository.findByUsername(username);
        }
        return user != null;
    }

    public Page<User> findAll(int page, int size){
        return userRepository.findAllByOrderByActiveDescIdAsc(PageRequest.of(page,size));
    }

    public List<LightUser>  findAllActiveUsers(){
        List<User>  users = userRepository.findAllByActiveIsTrueOrderByFirstNameAscLastNameAsc();
        return users
                .stream()
                .map(user -> new LightUser(user))
                .collect(Collectors.toList());
    }

    public List<User>  findAllUsers(){
        return userRepository.findAllByActiveIsTrueOrderByFirstNameAscLastNameAsc();
    }
}
