package az.enjooy.model.entity;

import az.enjooy.dto.wishlist.WishlistSaveDTO;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static javax.persistence.GenerationType.IDENTITY;

@Data
@NoArgsConstructor
@Entity
public class Wishlist {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    private String name;

    private LocalDateTime createdAt;

    private Long viewCount;

    private Boolean isPrivate;

    private Boolean hasBoughtSign;

    @OneToMany(cascade = CascadeType.ALL)
    private List<WishlistItem> wishListItems = new ArrayList<>();

    @ManyToOne
    private User owner;

    public Wishlist(WishlistSaveDTO wishlistSaveDTO, User owner) {
        this.id = wishlistSaveDTO.getId();
        this.name = wishlistSaveDTO.getName();
        this.isPrivate = wishlistSaveDTO.getIsPrivate();
        this.hasBoughtSign = wishlistSaveDTO.getHasBoughtSign();
        this.owner = owner;
        this.viewCount=0L;
        this.createdAt = LocalDateTime.now();
    }

    public void incrementViewCount() {
        this.viewCount++;
    }

    public void addItem(Item item) {
        this.wishListItems.add(new WishlistItem(item));
    }

    public Boolean isItemExists(Item item) {
        return this.getWishListItems().stream()
                .map(WishlistItem::getItem)
                .collect(Collectors.toList())
                .contains(item);
    }

    public WishlistItem getWishlistItemByItemId(Long itemId) {
        return this.getWishListItems().stream()
                .filter(x-> x.getItem().getId().longValue() ==itemId)
                .findFirst()
                .orElse(null);
    }

    public WishlistItem getItemById(Long id) {
        return this.wishListItems
                .stream()
                .filter(x->x.getId().longValue()==id)
                .findFirst()
                .orElse(null);
    }
}
