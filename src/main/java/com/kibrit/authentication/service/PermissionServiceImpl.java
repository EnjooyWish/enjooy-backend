package com.kibrit.authentication.service;

import com.kibrit.authentication.model.Permission;
import com.kibrit.authentication.repository.PermissionRepository;
import com.kibrit.authentication.service.abstraction.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PermissionServiceImpl implements PermissionService {

    @Autowired
    PermissionRepository permissionRepository;

    @Override
    public Map<String, List<Permission>> findAll() {
        Map<String, List<Permission>> permissionMap = new HashMap<>();
        List<Permission> permissions = permissionRepository.findAll();
        List<Permission> permissionList;
        for (Permission permission : permissions) {
            permissionList = permissionMap.get(permission.getModule().name());
            if (permissionList == null) {
                permissionList = new ArrayList<>();
                permissionMap.put(permission.getModule().name(), permissionList);
            }
            permissionList.add(permission);
        }
        return permissionMap;
    }
}
