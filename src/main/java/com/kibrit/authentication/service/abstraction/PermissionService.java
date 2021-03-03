package com.kibrit.authentication.service.abstraction;

import com.kibrit.authentication.model.Permission;

import java.util.List;
import java.util.Map;

public interface PermissionService {
    Map<String, List<Permission>> findAll();
}
