package az.enjooy.repository;

import az.enjooy.model.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    User findByUsername(String username);
    Optional<User> findById(Long id);
    void deleteById(Long id);

    Page<User> findAllByOrderByActiveDescFirstNameAscLastNameAsc(Pageable pageable);

    List<User> findAllByActiveIsTrueOrderByFirstNameAscLastNameAsc();

    @Transactional
    @Modifying
    @Query("UPDATE User u " + "SET u.active = TRUE WHERE u.email = ?1")
    void enableAppUser(String email);

}
