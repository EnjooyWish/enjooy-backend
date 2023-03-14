package az.enjooy.dto.wishlist;

import lombok.Data;

@Data
public class WishlistSaveDTO {
    private Long id;
    private String name;
    private String username;
    private Boolean isPrivate;
    private Boolean hasBoughtSign;
}
