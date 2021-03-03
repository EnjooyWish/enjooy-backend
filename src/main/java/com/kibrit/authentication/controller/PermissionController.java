package com.kibrit.authentication.controller;


import com.kibrit.authentication.model.Permission;
import com.kibrit.authentication.service.abstraction.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("permissions")
public class PermissionController {

    @Autowired
    PermissionService permissionService;

    @GetMapping
    public Map<String, List<Permission>> findAll() {
        return permissionService.findAll();
    }
}
