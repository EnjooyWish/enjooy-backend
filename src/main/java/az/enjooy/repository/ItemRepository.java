package az.enjooy.repository;

import az.enjooy.dto.item.ItemSearchDTO;
import az.enjooy.model.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long> {
    Item findByUrl(String url);
    List<Item> getAllByCategoryId(Long id);

    @Query("SELECT i" +
            " FROM Item i " +
            "WHERE (:#{#search.isCategoryIdExists()} = false or i.category.id = :#{#search.categoryId}) " +
            "and (:#{#search.isNameExists()} = false or i.name like lower(concat('%', concat(:#{#search.name}, '%')))) " +
            "and (:#{#search.isMinPriceExists()} = false or i.price >= :#{#search.minPrice}) " +
            "and (:#{#search.isMaxPriceExists()} = false or i.price <= :#{#search.maxPrice})")
    List<Item> search(ItemSearchDTO search);
}
