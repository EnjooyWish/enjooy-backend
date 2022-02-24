package com.kibrit.authentication.service;

import com.kibrit.authentication.dto.RoleDTO;
import com.kibrit.authentication.dto.UserDTO;
import com.kibrit.authentication.exception.UsernameAlreadyExistsException;
import com.kibrit.authentication.mapper.UserMapper;
import com.kibrit.authentication.model.Role;
import com.kibrit.authentication.model.User;
import com.kibrit.authentication.repository.UserDao;
import com.kibrit.authentication.repository.UserRepository;
import com.kibrit.authentication.service.abstraction.RoleService;
import com.kibrit.authentication.exception.ResourceNotFoundException;
import com.kibrit.authentication.model.LightUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserService {

    Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private  RoleService roleService;

    @Autowired
    private BCryptPasswordEncoder encoder;

    @Autowired
    private UserDao userDao;


    public User save(UserDTO userDTO) {
        logger.info("saving user {0}", userDTO);
        Set<Role> newRoles = new LinkedHashSet();
        Set<Role> roles = new LinkedHashSet();
        User user;
        if (userDTO.getId() != null) {
            user = findById(userDTO.getId());
        } else {
            user = new User();
        }
        checkUserExistence(userDTO.getUsername(), userDTO.getId());
        user.setUsername(userDTO.getUsername());
        user.setPhotoUrl(userDTO.getPhoto());
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setEmail(userDTO.getEmail());
        if (userDTO.getPassword() != null) {
            user.setPassword(encoder.encode(userDTO.getPassword()));
        }
        user.setUserNo(userDTO.getUserNo());
        for (Role role : new ArrayList<>(user.getRoles())) {
            role.removeUser(user);
            roles.add(role);
        }
        roleService.saveAll(roles);
        for (RoleDTO roleDTO : userDTO.getRoles()) {
            Role role = roleService.findById(roleDTO.getId());
            newRoles.add(role);
        }
        newRoles.forEach(newRole -> newRole.addUser(user));
        user.setRoles(newRoles);
        return userRepository.save(user);
    }


    public User activateOrDeactivate(Long id, boolean isActive) {
        User user = findById(id);
        user.setActive(isActive);
        return userRepository.save(user);
    }

    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found with id = " + id));
    }

    public UserDTO findByIdAndMapToDTO(Long id) {
        User user = findById(id);
        return mapUserToUserDTO(user);
    }

    public UserDTO mapUserToUserDTO(User user) {
         return UserMapper.MAPPER.fromEntity(user);
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public void deleteUser(Long id){
        userDao.delete(id);
    }

    public void checkUserExistence(String username, Long id) {
        User user = findByUsername(username);
        User existingUser = null;
        if (id != null) {
            existingUser = findById(id);
        }
        if((id == null && user != null) ||
                (existingUser != null && user != null && user.getId() != existingUser.getId())){
            throw new UsernameAlreadyExistsException("User with username " + username + " already exists");
        }
    }

    public boolean userExists(String username) {
        User user = null;
        if (username != null) {
            user = userRepository.findByUsername(username);
        }
        return user != null;
    }

    public Page<UserDTO> findAll(int page, int size) {
        Page<User> userPage = userRepository.findAllByOrderByActiveDescFirstNameAscLastNameAsc(PageRequest.of(page, size));
        return userPageToUserDtoPage(userPage);
    }

    public Page<UserDTO> userPageToUserDtoPage(Page<User> users) {
        Page<UserDTO> userDTOS = users.map(this::mapUserToUserDTO);
        return userDTOS;
    }

    public List<LightUser> findAllActiveUsers() {
        List<User> users = userRepository.findAllByActiveIsTrueOrderByFirstNameAscLastNameAsc();
        return users
                .stream()
                .map(user -> new LightUser(user))
                .collect(Collectors.toList());
    }

    public List<User> findAllUsers() {
        return userRepository.findAllByActiveIsTrueOrderByFirstNameAscLastNameAsc();
    }
}
