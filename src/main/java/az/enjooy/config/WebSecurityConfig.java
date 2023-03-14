package az.enjooy.config;

import az.enjooy.util.RoleConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;

@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Value("${security.security-realm}")
    private String securityRealm;

    private final UserDetailsService userDetailsService;

    public WebSecurityConfig(@Qualifier("appUserDetailsService") UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    @Bean
    public UserDetailsService userDetailsServiceBean() throws Exception {
        return super.userDetailsServiceBean();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
//                .sessionManagement()
//                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                .and()
//                .httpBasic()
//                .realmName(securityRealm)
//                .and()
                .csrf()
                .disable()
                .cors().disable()
                .authorizeRequests()
                .antMatchers(HttpMethod.GET, "/users/single/{id}").hasAuthority("get_user")
                .antMatchers(HttpMethod.GET, "/users").hasAuthority("get_all_users")
                .antMatchers(HttpMethod.GET, "/users/extended-list").hasAuthority("get_users_extended")
                .antMatchers(HttpMethod.POST, "/users/password-changing").hasAuthority("user_password_change")
                .antMatchers(HttpMethod.DELETE, "/users/{id}").hasAuthority("delete_user")
                .antMatchers(HttpMethod.PUT, "/users/{id}/activation").hasAuthority("activation_user")
                .antMatchers(HttpMethod.GET, "/client-messages").hasAuthority("get_client_message")
                .antMatchers(HttpMethod.POST, "/objects").hasAuthority("change_configurations")
                .antMatchers(HttpMethod.POST, "/item-categories").hasAuthority("save_item_category")
                .antMatchers(HttpMethod.DELETE, "/item-categories/{id}").hasAuthority("delete_item_category")
                .antMatchers(HttpMethod.POST, "/faqs").hasAuthority("save_faq")
                .antMatchers(HttpMethod.DELETE, "/faqs/{id}").hasAuthority("delete_faq")
                .antMatchers(HttpMethod.POST, "/wishlists").hasAuthority("save_wishlist")
                .antMatchers(HttpMethod.DELETE, "/wishlists").hasAuthority("delete_wishlist")
                .antMatchers(HttpMethod.POST, "/wishlist-items").hasAuthority("save_wishlist_item_umico")
                .antMatchers(HttpMethod.DELETE, "/wishlist-items").hasAuthority("delete_wishlist_item")
                .antMatchers(HttpMethod.GET, "/users/my-information").hasAuthority("get_user_info")
                .antMatchers(HttpMethod.POST, "/items/answer-rate").hasAuthority("save_item_rate")
                .and()
                .oauth2ResourceServer()
                .jwt()
                .jwtAuthenticationConverter(jwtAuthenticationConverter());
    }

    private static JwtAuthenticationConverter jwtAuthenticationConverter() {
        //var jwtGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
        //jwtGrantedAuthoritiesConverter.setAuthoritiesClaimName("resource_access.user.roles");
        //jwtGrantedAuthoritiesConverter.setAuthorityPrefix("ROLE_");
        var jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(new RoleConverter());
        return jwtAuthenticationConverter;
    }
}
