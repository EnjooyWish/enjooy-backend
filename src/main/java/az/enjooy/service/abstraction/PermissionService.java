package az.enjooy.service.abstraction;

import az.enjooy.model.entity.Permission;

import java.util.List;

public interface PermissionService {
    List<Permission> findAll();
}
