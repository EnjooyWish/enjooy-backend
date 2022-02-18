package com.kibrit.authentication.controller;


import com.kibrit.authentication.dto.RoleDTO;
import com.kibrit.authentication.model.Role;
import com.kibrit.authentication.model.User;
import com.kibrit.authentication.service.abstraction.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Min;
import java.util.List;

@RestController
@RequestMapping("roles")
@RequiredArgsConstructor
public class RoleController {

    private final RoleService roleService;

    @PostMapping
    public Role save(@RequestHeader(value = "loggedInUser") String user, @RequestBody Role role) {
        return roleService.save(user, role);
    }

    @GetMapping("/{id}")
    public Role findById(@PathVariable Long id) {
        return roleService.findById(id);
    }

    @GetMapping("/table")
    @Validated
    public Page<Role> findAll(@RequestParam @Min(0) int page, @RequestParam int size) {
        return roleService.findAllByPageSizeAndNumber(page, size);
    }

    @PostMapping("/user/assignment")
    public Role assignUserToRole(@RequestParam Long id, @RequestBody List<User> users) {
        return roleService.assignUserToRole(id, users);
    }

    @GetMapping
    public List<RoleDTO> findLightRole() {
        return roleService.findAll();
    }

    @DeleteMapping("/{id}")
    public void deleteRoleById(@PathVariable Long id) {
        roleService.deleteRoleById(id);
    }
}
