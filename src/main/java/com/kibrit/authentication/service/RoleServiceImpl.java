package com.kibrit.authentication.service;

import com.kibrit.authentication.exception.ResourceNotFoundException;
import com.kibrit.authentication.model.Role;
import com.kibrit.authentication.model.User;
import com.kibrit.authentication.repository.RoleRepository;
import com.kibrit.authentication.service.abstraction.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    RoleRepository roleRepository;

    @Override
    public Role save(Role role) {
        return roleRepository.save(role);
    }

    @Override
    public Role findById(Long id) {
        return roleRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Role not found with id = " + id));
    }

    @Override
    public Page<Role> findAllByPageSizeAndNumber(int page, int size) {
        return roleRepository.findAll(PageRequest.of(page,size));
    }

    @Override
    public Role assignUserToRole(Long id, List<User> users) {
        Role role = findById(id);
        role.getUsers().clear();
        role.getUsers().addAll(users);
        return roleRepository.save(role);
    }
}
