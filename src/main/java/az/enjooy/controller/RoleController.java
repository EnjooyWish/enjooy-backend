package az.enjooy.controller;


import az.enjooy.dto.RoleDTO;
import az.enjooy.model.entity.Role;
import az.enjooy.model.entity.User;
import az.enjooy.service.abstraction.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Min;
import java.util.List;

@RestController
@RequestMapping("roles")
@RequiredArgsConstructor
@CrossOrigin("*")
public class RoleController {

    private final RoleService roleService;

    @PostMapping
    public Role save(@RequestHeader(value = "username") String username, @RequestBody Role role) {
        return roleService.save(username, role);
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
