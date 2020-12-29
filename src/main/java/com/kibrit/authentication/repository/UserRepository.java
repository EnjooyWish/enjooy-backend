package com.kibrit.authentication.repository;

import com.kibrit.authentication.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
    Optional<User> findById(Long id);
    void deleteById(Long id);

    Page<User> findAllByOrderByActiveDescIdAsc(Pageable pageable);

    List<User> findAllByActiveIsTrueOrderByFirstNameAscLastNameAsc();
}
