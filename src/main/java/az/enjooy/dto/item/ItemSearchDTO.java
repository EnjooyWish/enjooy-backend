package az.enjooy.dto.item;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class ItemSearchDTO {
    Long categoryId;
    Double minPrice;
    Double maxPrice;
    String name;
    int page;
    int size;

    public Boolean isNameExists() {
        return name != null;
    }

    public Boolean isMinPriceExists() {
        return minPrice != null;
    }

    public Boolean isMaxPriceExists() {
        return maxPrice != null;
    }

    public Boolean isCategoryIdExists() {
        return categoryId != null;
    }

    public ItemSearchDTO(Long categoryId, String name, Double minPrice, Double maxPrice, int page, int size) {
        this.categoryId = categoryId;
        this.name = name;
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
        this.page = page;
        this.size = size;
    }

}
