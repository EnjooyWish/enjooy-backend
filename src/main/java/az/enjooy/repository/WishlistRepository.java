package az.enjooy.repository;

import az.enjooy.dto.wishlist.WishlistSearchDTO;
import az.enjooy.model.entity.User;
import az.enjooy.model.entity.Wishlist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface WishlistRepository extends JpaRepository<Wishlist, Long> {

    Wishlist findByName(String name);
    List<Wishlist> findAllByOwner(User owner);
    List<Wishlist> findAllByIsPrivate(Boolean isPrivate);

    @Query("SELECT w" +
            " FROM Wishlist w " +
            "WHERE (:#{#search.isOnlyPublicExists()} = false or w.isPrivate = :#{#search.onlyPublic}) " +
            "and (:#{#search.isUsernameExists()} = false or w.owner.username = :#{#search.username})" +
            "order by w.createdAt desc ")
    List<Wishlist> search(WishlistSearchDTO search);
    Wishlist findByNameAndOwner(String name, User owner);
}
