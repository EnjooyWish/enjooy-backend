package az.enjooy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
@EnableJpaRepositories
public class EnjooyApplication {

    public static void main(String[] args) {
        SpringApplication.run(EnjooyApplication.class, args);
    }

    @RequestMapping(value = "/user", produces =  "application/json")
    public Map<String, Object>  user(OAuth2Authentication user) {
        Map<String, Object> userInfo = new HashMap<>();
        userInfo.put(
                "user", user.getUserAuthentication().getPrincipal()
        );
        userInfo.put(
                "authorities", AuthorityUtils.authorityListToSet(user.getUserAuthentication().getAuthorities())
        );
        return userInfo;
    }
}
