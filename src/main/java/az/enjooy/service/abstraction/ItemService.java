package az.enjooy.service.abstraction;

import az.enjooy.dto.item.ItemFromUrlDTO;
import az.enjooy.dto.item.ItemGetDTO;
import az.enjooy.dto.item.ItemSaveDTO;
import az.enjooy.dto.item.ItemSearchDTO;
import az.enjooy.model.entity.Item;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ItemService {

    ItemFromUrlDTO getFromUrl(String url);
    Item save(Item item);
    Item save(ItemSaveDTO saveDTO);
    Page<ItemGetDTO> getAll(Long categoryId, String name, Double minPrice, Double maxPrice, int page, int size);
    Item getById(Long id);
    List<Item> getAll();
    Boolean isItemExistsByUrl(String path);
    Item getByUrl(String url);
}
