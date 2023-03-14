package az.enjooy.dto.wishlist;

import az.enjooy.dto.PageSearchDTO;
import lombok.Data;

@Data
public class WishlistSearchDTO extends PageSearchDTO {
    Boolean onlyPublic;
    String username;

    public Boolean isOnlyPublicExists() {
        return onlyPublic != null;
    }

    public Boolean isUsernameExists() {
        return username != null;
    }

    public WishlistSearchDTO(Boolean onlyPublic, String username, int page, int size) {
        this.onlyPublic = onlyPublic;
        this.username = username;
        this.setPage(page);
        this.setSize(size);
    }
}
