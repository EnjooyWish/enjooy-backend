package com.kibrit.authentication.repository;

import com.kibrit.authentication.model.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

public interface RoleRepository extends JpaRepository<Role,Long> {
    Page<Role> findAll(Pageable pageable);

    @Override
    void deleteById(Long id);
}
