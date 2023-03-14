package az.enjooy.dto.item;

import lombok.Data;

import java.util.List;

@Data
public class ItemGenerateDTO {
    private List<Long> answerIds;
    private String username;
}
