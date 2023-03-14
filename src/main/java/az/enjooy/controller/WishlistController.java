package az.enjooy.controller;

import az.enjooy.dto.ResponseDTO;
import az.enjooy.dto.WishlistItemDTO;
import az.enjooy.dto.item.ItemDTO;
import az.enjooy.dto.wishlist.WishListGetDTO;
import az.enjooy.dto.wishlist.WishlistSaveDTO;
import az.enjooy.model.entity.Wishlist;
import az.enjooy.service.abstraction.WishlistService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("wishlists")
@CrossOrigin("*")
public class WishlistController {

    private final WishlistService service;

    @GetMapping("sharing-url")
    public ResponseEntity<ResponseDTO> getSharingUrl(String username, String wishlistName) {
        return ResponseEntity.ok(service.getSharingUrl(username, wishlistName));
    }

    @GetMapping
    public ResponseEntity<ResponseDTO> getAll(Boolean onlyPublic, String username, int page, int size) {
        return new ResponseEntity<>(ResponseDTO.builder()
                .success(true)
                .data(service.findAll(onlyPublic, username, page, size))
                .build(), HttpStatus.OK);
    }

    @GetMapping("/{username}/{name}")
    public ResponseEntity<WishListGetDTO> getUserWishlist(@PathVariable String username, @PathVariable String name) {
        return ResponseEntity.ok(service.getByNameAndUsername(name, username));
    }

    @GetMapping("/{id}")
    public ResponseEntity<WishListGetDTO> getById(@PathVariable Long id) {
        return new ResponseEntity<>(service.getById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Wishlist> save(@RequestBody WishlistSaveDTO wishlistSaveDTO) {
        return new ResponseEntity<>(service.save(wishlistSaveDTO), HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<ResponseDTO> delete(Long id) {
        service.delete(id);
        return new ResponseEntity<>(ResponseDTO.builder().success(true).build(), HttpStatus.OK);
    }

    @PutMapping("{id}/item")
    public ResponseEntity<Wishlist> addItem(@PathVariable Long id, @RequestBody ItemDTO itemDTO) {
        return new ResponseEntity<>(service.addItem(id, itemDTO), HttpStatus.OK);
    }

    @PutMapping("/item-buying")
    public ResponseEntity<ResponseDTO> itemBuying(@RequestBody WishlistItemDTO itemDTO) {
        return new ResponseEntity<>(ResponseDTO.builder()
                .success(true)
                .data(service.handleItemBuying(itemDTO))
                .build(), HttpStatus.OK);
    }
}
