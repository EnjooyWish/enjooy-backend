package az.enjooy.controller;

import az.enjooy.dto.ResponseDTO;
import az.enjooy.dto.WishlistItemDTO;
import az.enjooy.dto.wishlist.WishlistItemByUrlDTO;
import az.enjooy.service.abstraction.WishlistService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("wishlist-items")
@CrossOrigin("*")
public class WishlistItemController {

    private final WishlistService service;

    @GetMapping(params = "wishlistId")
    public ResponseEntity<ResponseDTO> get(Long wishlistId) {
        return new ResponseEntity<>(service.getWishlistItemsById(wishlistId), HttpStatus.OK);
    }

    @DeleteMapping(params = {"id", "wishlistId"} )
    public ResponseEntity<ResponseDTO> delete(Long id, Long wishlistId) {
        return new ResponseEntity<>(service.deleteWishlistItemsById(id, wishlistId), HttpStatus.OK);
    }


    @PostMapping
    public ResponseEntity<ResponseDTO> post(@RequestBody WishlistItemByUrlDTO wishlistItemByUrlDTO) {
        return new ResponseEntity<>(service.addItemByUrl(wishlistItemByUrlDTO), HttpStatus.OK);
    }
}
