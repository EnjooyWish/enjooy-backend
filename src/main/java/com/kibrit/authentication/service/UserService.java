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
import com.kibrit.authentication.exception.ResourceNotFoundException;
import com.kibrit.authentication.model.LightUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Value("${default.password}")
    private String defaultPassword;

    final UserRepository userRepository;

    Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
     RoleRepository roleRepository;

    @Autowired
    RoleService roleService;

    @Autowired
    EntityManager entityManager;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public User save(UserDTO userDTO){

        logger.info("saving user"+userDTO);

        Set<Role> newRoles = new LinkedHashSet();
        Set<Role> roles = new LinkedHashSet();
        User user;
        if(userDTO.getId() != null){
            user = findById(userDTO.getId());
        }else {
            user = new User();
            //setDefaultPassword(user);
        }
        checkUserExistence(userDTO.getUsername(),userDTO.getId());
        user.setUsername(userDTO.getUsername());
        user.setPhoto(userDTO.getPhoto());
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setEmail(userDTO.getEmail());
        if(userDTO.getPassword()!=null)
            user.setPassword(bCryptPasswordEncoder.encode(userDTO.getPassword()));
        user.setUserNo(userDTO.getUserNo());

        for ( Role role : new ArrayList<>(user.getRoles())) {
              role.removeUser(user);
              roles.add(role);
        }
        roleRepository.saveAll(roles);
        for (RoleDTO roleDTO : userDTO.getRoles()){
            Role role = roleService.findById(roleDTO.getId());
            newRoles.add(role);
        }
        newRoles.forEach(newRole -> newRole.addUser(user));
        user.setRoles(newRoles);
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

    public UserDTO findByIdAndMapToDTO(Long id){
        User user = findById(id);
        return mapUserToDTO(user);
    }

    public UserDTO mapUserToDTO(User user){
        List<RoleDTO> roles = new ArrayList<>();
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setUsername(user.getUsername());
        userDTO.setFirstName(user.getFirstName());
        userDTO.setLastName(user.getLastName());
        userDTO.setPhoto(user.getPhoto());
        userDTO.setEmail(user.getEmail());
        userDTO.setActive(user.isActive());
//        userDTO.setPassword(user.getPassword());
        userDTO.setUserNo(user.getUserNo());
        for (Role role : user.getRoles()){
            RoleDTO roleDTO = new RoleDTO();
            roleDTO.setId(role.getId());
            roleDTO.setName(role.getName());
            roles.add(roleDTO);
        }
        userDTO.setRoles(roles);
        return userDTO;
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

    public void deleteUser(Long id){
        entityManager.createNativeQuery("DELETE from users_and_roles u where u.user_id= :userId")
                .setParameter("userId", id)
                .executeUpdate();
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

    public Page<UserDTO> findAll(int page, int size){
        Page<User> userPage = userRepository.findAllByOrderByActiveDescIdAsc(PageRequest.of(page,size));
        return toPageObjectDto(userPage);
    }

    public Page<UserDTO> toPageObjectDto(Page<User> users) {
        Page<UserDTO> userDTOS  = users.map(this::mapUserToDTO);
        return userDTOS;
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
