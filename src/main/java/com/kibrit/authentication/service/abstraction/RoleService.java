package com.kibrit.authentication.service.abstraction;

import com.kibrit.authentication.dto.RoleDTO;
import com.kibrit.authentication.model.Role;
import com.kibrit.authentication.model.User;
import org.springframework.data.domain.Page;

import java.util.List;

public interface RoleService {
    Role save(Role role);
    Role findById(Long id);
    Page<Role> findAllByPageSizeAndNumber(int page, int size);
    Role assignUserToRole(Long id, List<User> users);
    List<RoleDTO>  findAll();
}
