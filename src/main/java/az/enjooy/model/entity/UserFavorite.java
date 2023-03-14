package az.enjooy.model.entity;

import az.enjooy.dto.UserFavoritePostDTO;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Data
@NoArgsConstructor
public class UserFavorite {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @OneToOne
    @NotNull
    private User user;

    @OneToOne
    private Item item;

    public UserFavorite(User user, UserFavoritePostDTO postDTO, Item item) {
        this.user = user;
        this.id = postDTO.getId();
        this.item = item;
    }
}
