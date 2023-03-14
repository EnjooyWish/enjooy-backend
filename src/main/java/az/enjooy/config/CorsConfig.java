package az.enjooy.config;

import com.google.common.collect.ImmutableList;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsConfigurationSource;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

@Configuration
public class CorsConfig {

    @Bean
    CorsConfigurationSource corsConfiguration() {
        CorsConfiguration corsConfig = new CorsConfiguration();
        corsConfig.applyPermitDefaultValues();
        corsConfig.addAllowedMethod(HttpMethod.POST);
        corsConfig.addAllowedMethod(HttpMethod.GET);
        corsConfig.addAllowedMethod(HttpMethod.DELETE);
        corsConfig.addAllowedMethod(HttpMethod.PUT);
        corsConfig.setAllowedOrigins(ImmutableList.of("*"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/oauth/**", corsConfig);
        source.registerCorsConfiguration("/users/**", corsConfig);
        source.registerCorsConfiguration("/client-messages/**", corsConfig);
        source.registerCorsConfiguration("/configurations/**", corsConfig);
        source.registerCorsConfiguration("/faqs/**", corsConfig);
        source.registerCorsConfiguration("/item-categories/**", corsConfig);
        source.registerCorsConfiguration("/items/**", corsConfig);
        source.registerCorsConfiguration("/otp-codes/**", corsConfig);
        source.registerCorsConfiguration("/permissions/**", corsConfig);
        source.registerCorsConfiguration("/roles/**", corsConfig);
        source.registerCorsConfiguration("/statics/**", corsConfig);
        source.registerCorsConfiguration("/files/**", corsConfig);
        source.registerCorsConfiguration("/wishlists/**", corsConfig);
        source.registerCorsConfiguration("/objects/**", corsConfig);
        return source;
    }
}
