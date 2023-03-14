package az.enjooy.dto.wishlist;

import lombok.Data;

@Data
public class WishlistItemByUrlDTO {
    private Long wishlistItemId;
    private Long wishlistId;
    private String itemUrl;
    private String photoUrl;
    private String name;
    private Double price;
}
