package az.enjooy.dto.item;

import lombok.Data;

@Data
public class ItemSaveDTO {
    Long id;
    String name;
    Long categoryId;
    Double price;
    String description;
    String url;
    String photoUrl;
}
