package com.kibrit.authentication.service;

import com.kibrit.authentication.dto.AdminPasswordDTO;
import com.kibrit.authentication.dto.UserPasswordDTO;
import com.kibrit.authentication.dto.UserDTO;
import com.kibrit.authentication.dto.UserUpdateDTO;
import com.kibrit.authentication.exception.InvalidOldPasswordException;
import com.kibrit.authentication.mapper.UserMapper;
import com.kibrit.authentication.model.User;
import com.kibrit.authentication.repository.UserRepository;
import com.kibrit.authentication.security.JWTTokenStoreConfig;
import com.kibrit.authentication.util.GenericResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Value("${security.jwt.client-id}")
    private String clientId;

    @Autowired
    TokenStore tokenStore;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public User save(UserDTO userDTO){
        User user = UserMapper.MAPPER.mapFromUserDTO(userDTO);
        user.setPassword(bCryptPasswordEncoder.encode(userDTO.getPassword()));
        return userRepository.save(user);
    }

    public User update(UserUpdateDTO userUpdateDTO){
        User user = userRepository.findById(userUpdateDTO.getId()).get();
        user.setFirstName(userUpdateDTO.getFirstName());
        user.setLastName(userUpdateDTO.getLastName());
        user.setMiddleName(userUpdateDTO.getMiddleName());
        user.setEmail(userUpdateDTO.getEmail());
        user.setPhoto(userUpdateDTO.getPhoto());
        return userRepository.save(user);
    }

    public User findById(Long id){
        User user =  userRepository.findById(id).get();
        return user;
    }

    public User  findByUsername(String username){
        return userRepository.findByUsername(username);
    }

    public boolean isValidPassword(String password, String encryptedPassword){
        return BCrypt.checkpw(password,encryptedPassword);
    }

    public GenericResponse changePasswordByUser(User user, UserPasswordDTO passwordDto){
        if (!isValidPassword(passwordDto.getOldPassword(),user.getPassword())) {
            throw new InvalidOldPasswordException("Invalid old password");
        }else {
            user.setPassword(new BCryptPasswordEncoder().encode(passwordDto.getNewPassword()));
            userRepository.save(user);
        }
        return new GenericResponse("success", "Password changed successfully");
    }

    public GenericResponse  changePasswordByAdmin(User user, AdminPasswordDTO adminPasswordDTO){
        user.setPassword(new BCryptPasswordEncoder().encode(adminPasswordDTO.getNewPassword()));
        userRepository.save(user);
        return new GenericResponse("success", "Password changed successfully");
    }

    public void deleteUser(Long id){
        userRepository.deleteById(id);
    }
    public boolean userExists(String username){
        User user = null;
        if(username != null) {
            user = userRepository.findByUsername(username);
        }
        if( user != null){
            return true;
        }
        return false;
    }

    public void revokeAuthentication(User user) {
        Collection<OAuth2AccessToken> tokens = tokenStore.findTokensByClientId(clientId);
        for (OAuth2AccessToken token : tokens) {
            revokeToken(token.getValue());
        }
    }

    private boolean revokeToken(String tokenValue) {
        OAuth2AccessToken accessToken = tokenStore.readAccessToken(tokenValue);
        if (accessToken == null) {
            return false;
        }
        if (accessToken.getRefreshToken() != null) {
            tokenStore.removeRefreshToken(accessToken.getRefreshToken());
        }
        tokenStore.removeAccessToken(accessToken);
        return true;
    }

}
