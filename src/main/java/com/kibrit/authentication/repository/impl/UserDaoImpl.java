package com.kibrit.authentication.repository.impl;

import com.kibrit.authentication.repository.UserDao;
import com.kibrit.authentication.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class UserDaoImpl implements UserDao {

    private final EntityManager entityManager;

    private final UserRepository userRepository;

    @Override
    @Transactional
    public void delete(Long id) {
        entityManager.createNativeQuery("DELETE from users_and_roles u where u.user_id= :userId")
                .setParameter("userId", id)
                .executeUpdate();
        userRepository.deleteById(id);
    }
}
