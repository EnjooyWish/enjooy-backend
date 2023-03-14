package az.enjooy.dto;

import lombok.Data;

@Data
public class UserFavoritePostDTO {
    private Long id;
    private Long itemId;
    private String username;
}
