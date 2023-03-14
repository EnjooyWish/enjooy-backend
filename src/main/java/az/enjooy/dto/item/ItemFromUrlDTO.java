package az.enjooy.dto.item;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ItemFromUrlDTO {
    String name;
    String photoUrl;
    Double price;
    String url;
    String description;
}
