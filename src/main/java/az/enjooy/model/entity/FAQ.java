package az.enjooy.model.entity;

import az.enjooy.dto.FAQPostDTO;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Data
@NoArgsConstructor
public class FAQ {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    private String question;

    private String answer;

    private Boolean isActive = true;

    public FAQ(FAQPostDTO postDTO) {
        this.id = postDTO.getId();
        this.question = postDTO.getQuestion();
        this.answer = postDTO.getAnswer();
        this.isActive = postDTO.getIsActive();
    }
}
