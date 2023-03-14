package az.enjooy.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ItemRate {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    @JoinColumn(name = "item_id", referencedColumnName = "id")
    @ManyToOne
    private Item item;

    @JoinColumn(name = "answer_id", referencedColumnName = "id")
    @OneToOne
    private Answer answer;

    private Integer rate;
}
