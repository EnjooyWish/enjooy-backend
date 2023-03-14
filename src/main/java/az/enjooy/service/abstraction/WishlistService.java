package az.enjooy.service.abstraction;

import az.enjooy.dto.ResponseDTO;
import az.enjooy.dto.WishlistItemDTO;
import az.enjooy.dto.item.ItemDTO;
import az.enjooy.dto.wishlist.WishListGetDTO;
import az.enjooy.dto.wishlist.WishlistItemByUrlDTO;
import az.enjooy.dto.wishlist.WishlistSaveDTO;
import az.enjooy.model.entity.Wishlist;
import org.springframework.data.domain.Page;

public interface WishlistService {
    Wishlist save(WishlistSaveDTO wishlistSaveDTO);

    void delete(Long id);

    Page<WishListGetDTO> findAll(Boolean onlyPublic, String username, int page, int size);

    WishListGetDTO getById(Long id);

    Wishlist addItem(Long wishlistId, ItemDTO itemDTO);

    ResponseDTO addItemByUrl(WishlistItemByUrlDTO wishlistItemByUrlDTO);

    ResponseDTO getWishlistItemsById(Long id);

    ResponseDTO deleteWishlistItemsById(Long id, Long wishlistId);

    ResponseDTO getSharingUrl(String username, String wishlistName);

    Boolean handleItemBuying(WishlistItemDTO wishlistItemDTO);

    WishListGetDTO getByNameAndUsername(String name, String username);

}
