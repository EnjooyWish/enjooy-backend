package az.enjooy.service.implementation;

import az.enjooy.dto.item.ItemGetDTO;
import az.enjooy.model.entity.User;
import az.enjooy.model.entity.UserRecommend;
import az.enjooy.repository.UserRecommendRepository;
import az.enjooy.service.abstraction.UserRecommendService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserRecommendServiceImpl implements UserRecommendService {

    private final UserRecommendRepository repository;
    private final UserServiceImpl userService;

    @Override
    public List<ItemGetDTO> getRecommendItemsByUsername(String username) {
        UserRecommend userRecommend = getByUser(userService.findByUsername(username));
        List<ItemGetDTO> items = new ArrayList<>();
        userRecommend.getItems().forEach(x-> items.add(new ItemGetDTO(x)));
        return items;
    }

    @Override
    public UserRecommend save(UserRecommend userRecommend) {
        UserRecommend userRecommendInDB = getByUser(userRecommend.getUser());
        if(userRecommendInDB!=null) {
            userRecommendInDB.setUser(null);
            userRecommendInDB.setItems(null);
            deleteById(userRecommendInDB.getId());
        }
        return repository.save(userRecommend);
    }

    @Override
    public UserRecommend getByUser(User user) {
        return repository.getByUser(user);
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
