package com.kibrit.authentication.controller;

import com.kibrit.authentication.dto.UserPasswordDTO;
import com.kibrit.authentication.dto.UserDTO;
import com.kibrit.authentication.model.User;
import com.kibrit.authentication.service.UserService;
import com.kibrit.authentication.util.GenericResponse;
import io.swagger.annotations.ApiOperation;
import org.json.JSONObject;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import javax.validation.constraints.Min;

@RestController
@RequestMapping("users")
public class UserController {

    final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    @Validated
    public Page<User> findAll(@RequestParam @Min(0) int page, @RequestParam int size){
        return userService.findAll(page,size);
    }
    @ApiOperation(value = "Create or update user")
    @PostMapping
    public User  save(@Valid @RequestBody UserDTO userDTO){
         return userService.save(userDTO);
    }

    @ApiOperation(value = "Change user password", response = GenericResponse.class)
    @PostMapping("/password/change")
    public GenericResponse changePassword(@RequestHeader(value = "loggedInUser") String user,
                                          @Valid @RequestBody UserPasswordDTO passwordDTO){
        JSONObject  jsonObject = new JSONObject(user);
        User authenticatedUser = userService.findById(jsonObject.getLong("id"));
         return userService.changePasswordByUser(authenticatedUser,passwordDTO);
    }

    @ApiOperation(value = "User password reset")
    @PostMapping("/{id}/password/reset")
    public void resetPassword(@PathVariable Long id){
         userService.resetPassword(id);
    }

    @ApiOperation(value = "Activate or deactivate user", response = User.class)
    @PostMapping("/{id}/activation")
    public User activateOrDeactivateUser(@PathVariable Long id, @RequestParam boolean isActive){
        return userService.activateOrDeactivate(id,isActive);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id)
    {
        userService.deleteUser(id);
    }

    @GetMapping("/{id}")
    public User findById(@PathVariable Long id){
        return userService.findById(id);
    }

}
