package az.enjooy.dto.wishlist;

import az.enjooy.model.entity.User;
import az.enjooy.model.entity.Wishlist;
import az.enjooy.model.entity.WishlistItem;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class WishListGetDTO {

    Long id;

    String name;

    LocalDateTime createdAt;

    Long viewCount;

    Boolean isPrivate;

    Boolean hasBoughtSign;

    Long boughtItemCount;

    Long allItemCount;

    List<WishlistItem> wishListItems;

    User owner;

    public WishListGetDTO(Wishlist wishlist) {
        this.id = wishlist.getId();
        this.name = wishlist.getName();
        this.createdAt = wishlist.getCreatedAt();
        this.viewCount = wishlist.getViewCount();
        this.isPrivate = wishlist.getIsPrivate();
        this.hasBoughtSign = wishlist.getHasBoughtSign();
        this.wishListItems = wishlist.getWishListItems();
        this.owner = wishlist.getOwner();
        if(wishlist.getWishListItems()!=null) {
            this.allItemCount = wishlist.getWishListItems().stream().count();
            this.boughtItemCount = wishlist.getWishListItems().stream().filter(WishlistItem::getIsBought).count();
        }
    }
}
