package az.enjooy.model.entity;

import lombok.Data;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Data
public class ItemCategory {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    private String name;

    private String fullPath;

    @JoinColumn(name = "parent_id", referencedColumnName = "id")
    @ManyToOne
    private ItemCategory parent;
}
