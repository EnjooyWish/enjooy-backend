package az.enjooy.repository;

import az.enjooy.model.entity.User;
import az.enjooy.model.entity.UserRecommend;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRecommendRepository extends JpaRepository<UserRecommend, Long> {
    UserRecommend getByUser(User user);
}
