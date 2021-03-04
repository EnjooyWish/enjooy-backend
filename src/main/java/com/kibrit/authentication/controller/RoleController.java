package com.kibrit.authentication.controller;


import com.kibrit.authentication.model.Role;
import com.kibrit.authentication.model.User;
import com.kibrit.authentication.service.abstraction.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Min;
import java.util.List;

@RestController
@RequestMapping("roles")
public class RoleController {
    @Autowired
    RoleService roleService;

    @PostMapping
    public Role save(@RequestBody Role role){
        return roleService.save(role);
    }

    @GetMapping("/{id}")
    public Role findById(@PathVariable Long id){
        return roleService.findById(id);
    }

    @GetMapping("/table")
    @Validated
    public Page<Role> findAll(@RequestParam @Min(0) int page, @RequestParam int size){
        return roleService.findAllByPageSizeAndNumber(page,size);
    }

    @PostMapping("/user/assignment")
    public Role assignUserToRole(@RequestParam Long id, @RequestBody List<User> users){
        return roleService.assignUserToRole(id,users);
    }
}
