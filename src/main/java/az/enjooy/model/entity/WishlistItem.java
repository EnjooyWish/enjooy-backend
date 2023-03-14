package az.enjooy.model.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@NoArgsConstructor
@Data
public class WishlistItem {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @ManyToOne
    private Item item;

    private Boolean isBought;

    public WishlistItem(Item item) {
        this.item = item;
        this.isBought = false;
    }
}
