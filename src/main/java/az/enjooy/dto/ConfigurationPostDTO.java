package az.enjooy.dto;

import az.enjooy.model.entity.InnerItem;
import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class ConfigurationPostDTO {
    private Long id;
    private String type;
    private String name;
    private String value;
    private String photoUrl;
    private Map<Long, String> tags = new HashMap<>();
    private List<InnerItem> innerItems = new ArrayList<>();

}
