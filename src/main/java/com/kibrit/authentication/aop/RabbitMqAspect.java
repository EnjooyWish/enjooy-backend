package com.kibrit.authentication.aop;

import com.kibrit.authentication.dto.UserDTO;
import com.kibrit.authentication.model.User;
import com.kibrit.authentication.service.UserService;
import com.kibrit.authentication.service.abstraction.PublisherService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Aspect
@Service
public class RabbitMqAspect {
    private Logger logger =  LoggerFactory.getLogger(this.getClass());

    @Autowired
    PublisherService publisherService;

    @Autowired
    UserService userService;

    @Pointcut(value= "execution(* com.kibrit.authentication.service.UserService.save(..))")
    public void saveMethod() {};

    @Pointcut(value= "execution(* com.kibrit.authentication.service.UserService.deleteUser(..))")
    public void deleteMethod() {};



    @Around("saveMethod()")
    public User sendUserUpdateEvent(ProceedingJoinPoint pjp) throws Throwable {
        User oldUser;
        boolean isUserUpdated = false;
        UserDTO userDTO = (UserDTO)  pjp.getArgs()[0];
        if(userDTO.getId() != null) {
            oldUser = userService.findById(userDTO.getId());
            isUserUpdated = !oldUser.getFullName().equals(userDTO.getFullName());
        }
        User user = (User) pjp.proceed();
        if(isUserUpdated) {
            publisherService.publishUserEvent(user,"user.update");
        }
        if(userDTO.getId() == null){
            publisherService.publishUserEvent(user, "user.update");
        }
        return user;
    }

    @Around("deleteMethod()")
    public void sendUserDeletedEvent(ProceedingJoinPoint pjp) throws Throwable {
        Long userId =(Long)pjp.getArgs()[0];
        User oldUser = userService.findById(userId);
        pjp.proceed();
        publisherService.publishUserEvent(oldUser,"user.delete");
    };
}
