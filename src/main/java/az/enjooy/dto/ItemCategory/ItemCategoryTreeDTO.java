package az.enjooy.dto.ItemCategory;

import az.enjooy.model.entity.ItemCategory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ItemCategoryTreeDTO {
    private Long id;
    private String name;
    private Long parentId;
    private String parentName;
    private String fullPath;
    private List<ItemCategoryTreeDTO> children = new ArrayList<>();

    public ItemCategoryTreeDTO(ItemCategory category) {
        this.id = category.getId();
        this.name = category.getName();
        if(this.parentId !=null && this.parentName!=null) {
            this.parentId = category.getParent().getId();
            this.parentName = category.getParent().getName();
        }
        this.fullPath = category.getFullPath();
    }
}
