package az.enjooy.service.abstraction;

import az.enjooy.dto.ItemCategory.ItemCategorySaveDTO;
import az.enjooy.dto.ItemCategory.ItemCategoryTreeDTO;
import az.enjooy.model.entity.ItemCategory;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ItemCategoryService {
    List<ItemCategoryTreeDTO> getAll();
    Page<ItemCategoryTreeDTO> getAllParents(int page, int size);
    ItemCategory save(ItemCategorySaveDTO saveDTO);
    void delete(Long id);
    ItemCategory getById(Long id);
}
