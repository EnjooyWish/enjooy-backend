package com.kibrit.authentication.repository;

import com.kibrit.authentication.model.Permission;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PermissionRepository extends JpaRepository<Permission,Long> {
    List<Permission> findAll();
}
