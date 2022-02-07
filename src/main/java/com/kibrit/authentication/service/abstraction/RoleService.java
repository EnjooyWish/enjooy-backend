package com.kibrit.authentication.service.abstraction;

import com.kibrit.authentication.dto.RoleDTO;
import com.kibrit.authentication.model.Role;
import com.kibrit.authentication.model.User;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Set;

public interface RoleService {
    Role save(String user,Role role);
    Role findById(Long id);
    Page<Role> findAllByPageSizeAndNumber(int page, int size);
    Role assignUserToRole(Long id, List<User> users);
    List<RoleDTO>  findAll();
    List<Role> saveAll(Set<Role> roles);
    void deleteRoleById(Long id);
}
