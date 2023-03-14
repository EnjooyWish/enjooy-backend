package az.enjooy.controller;


import az.enjooy.service.abstraction.PermissionService;
import az.enjooy.model.entity.Permission;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("permissions")
@RequiredArgsConstructor
@CrossOrigin("*")
public class PermissionController {

    final PermissionService permissionService;

    @GetMapping
    public ResponseEntity<List<Permission>> findAll() {
        return new ResponseEntity<>(permissionService.findAll(), HttpStatus.OK);
    }
}
