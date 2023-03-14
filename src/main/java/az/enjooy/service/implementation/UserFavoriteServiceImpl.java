package az.enjooy.service.implementation;

import az.enjooy.dto.UserFavoritePostDTO;
import az.enjooy.dto.UserFavoriteSearchDTO;
import az.enjooy.model.entity.Item;
import az.enjooy.model.entity.User;
import az.enjooy.model.entity.UserFavorite;
import az.enjooy.repository.UserFavoriteRepository;
import az.enjooy.service.abstraction.ItemService;
import az.enjooy.service.abstraction.UserFavoriteService;
import az.enjooy.util.ListUtil;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserFavoriteServiceImpl implements UserFavoriteService {

    private final UserFavoriteRepository repository;
    private final UserServiceImpl userService;
    private final ItemService itemService;

    @Override
    public UserFavorite post(UserFavoritePostDTO postDTO) {
        User authenticatedUser = userService.findByUsername(postDTO.getUsername());
        return repository.save(new UserFavorite(authenticatedUser, postDTO, itemService.getById(postDTO.getItemId())));
    }

    @Override
    public Page<Item> getAll(String username, int page, int size) {
        User user = userService.findByUsername(username);
        List<UserFavorite> favorites;
        if (user == null)
            favorites = repository.findAll();
        else
            favorites = repository.findAllByUser(user);
        List<Item> items = favorites.stream().map(UserFavorite::getItem).collect(Collectors.toList());
        return ListUtil.convertToPage(items, size, page);
    }
}
