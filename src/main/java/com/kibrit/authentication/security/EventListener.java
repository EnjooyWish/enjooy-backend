//package com.kibrit.authentication.security;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.ApplicationListener;
//import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.web.authentication.WebAuthenticationDetails;
//import org.springframework.stereotype.Component;
//
//@Component
//public class EventListener
//        implements ApplicationListener<AuthenticationSuccessEvent> {
//
//    public void onApplicationEvent(AuthenticationSuccessEvent e) {
//        System.out.println(e);
//         e.getAuthentication().getName();
////        System.out.println(authentication.getPrincipal());
//        WebAuthenticationDetails auth = (WebAuthenticationDetails)
//                e.getAuthentication().getDetails();
//        System.out.println("Logged in");
//    }
//}