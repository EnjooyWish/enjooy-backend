package com.kibrit.authentication.service;

import com.kibrit.authentication.model.User;
import com.kibrit.authentication.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class UserServiceTest {

    @InjectMocks
    UserService userService;

    @Mock
    UserRepository userRepository;

    @Mock
    BCryptPasswordEncoder bCryptPasswordEncoder;

    User user;

    @BeforeEach
    void setUp() throws Exception{
        MockitoAnnotations.initMocks(this);
        user = new User();
        user.setId(1L);
        user.setUsername("saliyev");
        user.setPhoto("photo");
        user.setEmail("seymur.aliyev@gmail.com");
        user.setFirstName("Seymur");
        user.setLastName("Əliyev");
        user.setMiddleName("Kərəm");
        user.setPassword(bCryptPasswordEncoder.encode("Seymur123"));
    }

    @Test
    void save() {
        when(userRepository.save(any(User.class))).thenReturn(user);
    }

    @Test
    void update() {
    }

    @Test
    void findById() {
        when(userService.findById(1L)).thenReturn(user);
    }

    @Test
    void findByUsername() {
    }

    @Test
    void isValidPassword() {
    }

    @Test
    void changePassword() {
    }

    @Test
    void userExists() {
    }
}