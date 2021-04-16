package com.kibrit.authentication.service;

import com.kibrit.authentication.dto.RoleDTO;
import com.kibrit.authentication.exception.ResourceNotFoundException;
import com.kibrit.authentication.model.Role;
import com.kibrit.authentication.model.User;
import com.kibrit.authentication.repository.RoleRepository;
import com.kibrit.authentication.service.abstraction.RoleService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    UserService userService;

    @Override
    public Role save(String user, Role role) {
        JSONObject jsonObject = new JSONObject(user);
        User authenticatedUser = userService.findById(jsonObject.getLong("id"));
        if(role.getId() == null){
            role.setCreatedBy(authenticatedUser);
        }
        role.setLastModifiedBy(authenticatedUser);
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

    @Override
    public List<RoleDTO> findAll() {
        List<Role> roles = roleRepository.findAll();
       return roles
                .stream()
                .map(role -> new RoleDTO(role.getId(),role.getName()))
                .collect(Collectors.toList());
    }
}
