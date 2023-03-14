package az.enjooy.dto.wishlist;

import az.enjooy.model.entity.Item;
import az.enjooy.model.entity.WishlistItem;
import lombok.Data;

@Data
public class WishlistItemGetDTO {
    private Long wishlistItemId;
    private Long itemId;
    private String name;
    private Double price;
    private String photoUrl;
    private Boolean isBought;

    public WishlistItemGetDTO(WishlistItem wishlistItem) {

        Item item = wishlistItem.getItem();
        this.wishlistItemId = wishlistItem.getId();
        this.itemId = item.getId();
        this.name = item.getName();
        this.price = item.getPrice();
        this.photoUrl = item.getPhotoUrl();
        this.isBought = wishlistItem.getIsBought();
    }
}
