package com.kibrit.authentication.controller;

import com.kibrit.authentication.dto.AdminPasswordDTO;
import com.kibrit.authentication.dto.UserPasswordDTO;
import com.kibrit.authentication.dto.UserDTO;
import com.kibrit.authentication.dto.UserUpdateDTO;
import com.kibrit.authentication.model.User;
import com.kibrit.authentication.service.UserService;
import com.kibrit.authentication.util.GenericResponse;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

@RestController
@RequestMapping("users")
public class UserController {
    @Autowired
    UserService userService;

    @ApiOperation(value = "Create new user")
    @PostMapping
    public User  save(@Valid @RequestBody UserDTO userDTO){
         return userService.save(userDTO);
    }

    @ApiOperation(value = "Update existing user")
    @PostMapping("/update")
    public User  update(@Valid @RequestBody UserUpdateDTO userUpdateDTO){
        return userService.update(userUpdateDTO);
    }

    @ApiOperation(value = "Change user password", response = GenericResponse.class)
    @PostMapping("/password/change")
    public GenericResponse changePassword(Principal principal, @Valid UserPasswordDTO passwordDTO){
         final User user = userService.findByUsername(principal.getName());
         return userService.changePasswordByUser(user,passwordDTO);
    }

    @ApiOperation(value = "User password change by admin", response = GenericResponse.class)
    @PostMapping("{id}/password/change/by/admin")
    public GenericResponse changePasswordByAdmin(@PathVariable Long id, @Valid AdminPasswordDTO adminPasswordDTO){
        final User user = userService.findById(id);
        return userService.changePasswordByAdmin(user,adminPasswordDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id)
    {
        userService.deleteUser(id);
    }




}
