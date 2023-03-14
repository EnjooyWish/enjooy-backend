package az.enjooy.service.abstraction;

import az.enjooy.dto.UserFavoritePostDTO;
import az.enjooy.dto.UserFavoriteSearchDTO;
import az.enjooy.model.entity.Item;
import az.enjooy.model.entity.UserFavorite;
import org.springframework.data.domain.Page;

public interface UserFavoriteService {
    Page<Item> getAll(String username, int page, int size);
    UserFavorite post(UserFavoritePostDTO postDTO);
}
