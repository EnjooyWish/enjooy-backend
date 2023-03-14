package az.enjooy.service.implementation;

import az.enjooy.repository.PermissionRepository;
import az.enjooy.service.abstraction.PermissionService;
import az.enjooy.model.entity.Permission;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PermissionServiceImpl implements PermissionService {

    private final PermissionRepository permissionRepository;

    @Override
    public List<Permission> findAll() {
        return permissionRepository.findAll();
    }
}
