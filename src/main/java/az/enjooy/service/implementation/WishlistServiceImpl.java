package az.enjooy.service.implementation;

import az.enjooy.dto.ResponseDTO;
import az.enjooy.dto.WishlistItemDTO;
import az.enjooy.dto.item.ItemDTO;
import az.enjooy.dto.item.ItemFromUrlDTO;
import az.enjooy.dto.wishlist.*;
import az.enjooy.exception.ResourceAlreadyExistsException;
import az.enjooy.exception.ResourceNotFoundException;
import az.enjooy.model.entity.Item;
import az.enjooy.model.entity.User;
import az.enjooy.model.entity.Wishlist;
import az.enjooy.model.entity.WishlistItem;
import az.enjooy.repository.WishlistRepository;
import az.enjooy.service.abstraction.ItemService;
import az.enjooy.service.abstraction.WishlistService;
import az.enjooy.util.ListUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class WishlistServiceImpl implements WishlistService {

    private final WishlistRepository repository;
    private final ItemService itemService;
    private final UserServiceImpl userService;
    private final Environment environment;

    @Override
    public ResponseDTO getSharingUrl(String username, String wishlistName) {
        return ResponseDTO.builder()
                .success(true)
                .data(environment.getProperty("base.url")+"/wishlists/"+username+"/"+wishlistName)
                .build();
    }

    @Override
    public ResponseDTO deleteWishlistItemsById(Long id, Long wishlistId) {
        Wishlist wishlist = repository.findById(wishlistId).orElse(null);
        wishlist.getWishListItems().removeIf(x -> x.getId().longValue() == id);
        repository.save(wishlist);
        return ResponseDTO.builder()
                .success(true)
                .build();
    }

    @Override
    public ResponseDTO getWishlistItemsById(Long id) {
        Wishlist wishlist = repository.findById(id).orElse(null);
        List<WishlistItemGetDTO> items = new ArrayList<>();
        if (wishlist.getWishListItems() != null) {
            wishlist.getWishListItems().forEach(x -> items.add(new WishlistItemGetDTO(x)));
        }
        return ResponseDTO.builder()
                .success(true)
                .data(items)
                .build();
    }

    @Override
    public WishListGetDTO getByNameAndUsername(String name, String username) {
        Wishlist wishlist = repository.findByNameAndOwner(name, userService.findByUsername(username));
        if (wishlist == null)
            throw new ResourceNotFoundException();
        return new WishListGetDTO(wishlist);
    }

    @Override
    public Boolean handleItemBuying(WishlistItemDTO wishlistItemDTO) {
        Wishlist wishlist = repository.findById(wishlistItemDTO.getWishlistId()).orElse(null);
        WishlistItem item = wishlist.getItemById(wishlistItemDTO.getWishlistItemId());
        item.setIsBought(!item.getIsBought());
        repository.save(wishlist);
        return item.getIsBought();
    }

    @Override
    public Wishlist addItem(Long wishlistId, ItemDTO itemDTO) {
        Wishlist wishlist = repository.findById(wishlistId).orElse(null);
        Item item = itemService.save(new Item(itemDTO, false));
        if (!wishlist.isItemExists(item))
            wishlist.addItem(item);
        return repository.save(wishlist);
    }

    @Override
    public ResponseDTO addItemByUrl(WishlistItemByUrlDTO dto) {
        Wishlist wishlist = repository.findById(dto.getWishlistId()).orElse(null);
        ItemFromUrlDTO itemFromUrl = itemService.getFromUrl(dto.getItemUrl());
        Item item = itemService.save(new Item(itemFromUrl, false));
        if (!wishlist.isItemExists(item))
            wishlist.addItem(item);
        repository.save(wishlist);
        dto.setWishlistItemId(wishlist.getWishlistItemByItemId(item.getId()).getId());
        dto.setName(item.getName());
        dto.setPrice(item.getPrice());
        dto.setPhotoUrl(item.getPhotoUrl());
        return ResponseDTO.builder()
                .success(true)
                .data(dto)
                .build();
    }

    @Override
    public Page<WishListGetDTO> findAll(Boolean onlyPublic, String username, int page, int size) {
        WishlistSearchDTO searchDTO = new WishlistSearchDTO(onlyPublic, username, page, size);
        searchDTO.setOnlyPublic(!searchDTO.getOnlyPublic());
        if(searchDTO.getOnlyPublic())
            searchDTO.setOnlyPublic(null);
        List<Wishlist> wishlistsInDB = repository.search(searchDTO);
        List<WishListGetDTO> wishlists = new ArrayList<>();
        wishlistsInDB.forEach(x -> wishlists.add(new WishListGetDTO(x)));
        return ListUtil.convertToPage(wishlists, searchDTO.getSize(), searchDTO.getPage());
    }

    @Override
    public WishListGetDTO getById(Long id) {
        Wishlist wishlist = repository.findById(id).orElse(null);
        if (wishlist == null) throw new ResourceNotFoundException();
        wishlist.incrementViewCount();
        repository.save(wishlist);
        return new WishListGetDTO(wishlist);
    }

    @Override
    public Wishlist save(WishlistSaveDTO wishlistSaveDTO) {
        String name = wishlistSaveDTO.getName();
        User owner = userService.findByUsername(wishlistSaveDTO.getUsername());
        Wishlist wishlistInDB = repository.findByNameAndOwner(name, owner);
        if (wishlistInDB != null && wishlistInDB.getId() != null) {
            if (wishlistInDB.getName().equals(wishlistSaveDTO.getName()) && wishlistSaveDTO.getId() != null) {
                wishlistInDB.setIsPrivate(wishlistSaveDTO.getIsPrivate());
                wishlistInDB.setHasBoughtSign(wishlistSaveDTO.getHasBoughtSign());
                return repository.save(wishlistInDB);
            }
            throw new ResourceAlreadyExistsException();
        }
        return repository.save(new Wishlist(wishlistSaveDTO, userService.findByUsername(owner.getUsername())));
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

//    @Override
//    public List<WishListGetDTO> findAllByUser(String username) {
//        JSONObject jsonObject = new JSONObject(username);
//        User owner = userService.findByUsername(jsonObject.getString("username"));
//        List<WishListGetDTO> wishlists = new ArrayList<>();
//        repository.findAllByOwner(owner).forEach(x->{
//            wishlists.add(new WishListGetDTO(x));
//        });
//        return wishlists;
//    }
}
