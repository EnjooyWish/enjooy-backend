package az.enjooy.repository;

import az.enjooy.model.entity.Permission;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PermissionRepository extends JpaRepository<Permission,Long> {
    List<Permission> findAll();
}
