package az.enjooy.model.entity;

import az.enjooy.dto.ConfigurationPostDTO;
import javassist.runtime.Inner;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@Entity
public class Config {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String type;

    private String name;

    @Column(length = 5000)
    private String value;

    private String photoUrl;

    @ElementCollection
    private Map<Long, String> tags;

    @OneToMany(cascade = CascadeType.ALL)
    private List<InnerItem> innerItems;

    public Config(ConfigurationPostDTO postDTO) {
        this.id = postDTO.getId();
        this.type = postDTO.getType();
        this.name = postDTO.getName();
        this.value = postDTO.getValue();
        this.photoUrl = postDTO.getPhotoUrl();
        this.tags = postDTO.getTags();
        this.innerItems = postDTO.getInnerItems();
    }
}