package az.enjooy.service.abstraction;

import az.enjooy.dto.item.ItemGetDTO;
import az.enjooy.model.entity.User;
import az.enjooy.model.entity.UserRecommend;

import java.util.List;

public interface UserRecommendService {
    UserRecommend save(UserRecommend userRecommend);
    UserRecommend getByUser(User user);

    void deleteById(Long id);

    List<ItemGetDTO> getRecommendItemsByUsername(String username);
}
