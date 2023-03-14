package az.enjooy.security;


import az.enjooy.repository.UserRepository;
import az.enjooy.model.entity.Permission;
import az.enjooy.model.entity.Role;
import az.enjooy.model.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JWTTokenEnhancer implements TokenEnhancer {
    @Autowired
    private UserRepository userRepository;

    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
        Map<String, Object> additionalInfo = new HashMap<>();
        User user = userRepository.findByUsername(authentication.getName());
        additionalInfo.put("userID", user.getId());
        additionalInfo.put("firstName", user.getFirstName());
        additionalInfo.put("lastName",user.getLastName());
        additionalInfo.put("fullName",user.getFirstName() + " " + user.getLastName());
        additionalInfo.put("username", user.getUsername());
        additionalInfo.put("authorities",getPermissionList(user));
        ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(additionalInfo);
        return accessToken;
    }

    public List<String> getPermissionList(User user) {
        List<String>  permissionList = new ArrayList<>();
        for (final Role role : user.getRoles()) {
           permissionList.add("ROLE_" + role.getName());
            for (Permission permission : role.getPermissions()){
                if(!permissionList.contains(permission.getName())) {
                    permissionList.add(permission.getName());
                }
            }
        }
        return permissionList;
    }
}
