//package com.kibrit.authentication.config;
//
//import com.kibrit.authentication.model.User;
//import com.kibrit.authentication.repository.UserRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
//import org.springframework.security.core.context.SecurityContextHolder;
//
//import java.security.Principal;
//import java.util.Optional;
//
//@Configuration
//@EnableJpaAuditing(auditorAwareRef = "auditorProvider")
//public class JpaAuditingConfiguration {
//
//    @Autowired
//    UserRepository userRepository;
//
//    @Bean
//    public Optional<User> auditorProvider() {
//        Principal principal = (Principal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//
//        /*
//          if you are using spring security, you can get the currently logged username with following code segment.
//          SecurityContextHolder.getContext().getAuthentication().getName()
//         */
//        return Optional.ofNullable(userRepository.findByUsername(principal.getName()));
//    }
//}