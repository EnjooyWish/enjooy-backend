package az.enjooy.service.implementation;

import az.enjooy.exception.ResourceNotFoundException;
import az.enjooy.exception.RoleHasUsers;
import az.enjooy.model.entity.User;
import az.enjooy.repository.RoleRepository;
import az.enjooy.dto.RoleDTO;
import az.enjooy.model.entity.Role;
import az.enjooy.service.abstraction.RoleService;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    private final UserServiceImpl userService;

    @Override
    public Role save(String user, Role role) {
        if (role.getId() == null && roleRepository.findByName(role.getName()) != null)
            throw new RuntimeException("Role " + role.getName() + " is already exists");
        JSONObject jsonObject = new JSONObject(user);
        User authenticatedUser = userService.findByUsername(jsonObject.getString("username"));
        if (role.getId() == null) {
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
        return roleRepository.findAll(PageRequest.of(page, size));
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
                .map(role -> new RoleDTO(role.getId(), role.getName()))
                .collect(Collectors.toList());
    }

    @Override
    public List<Role> saveAll(Set<Role> roles) {
        return roleRepository.saveAll(roles);
    }

    @Override
    public void deleteRoleById(Long id) {
        Role role = findById(id);
        if (role.getUsers().size() == 0) {
            roleRepository.deleteById(id);
        } else {
            throw new RoleHasUsers("This role has bounded users. Please, detach users from the role");
        }
    }
}
