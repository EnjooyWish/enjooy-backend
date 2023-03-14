package az.enjooy.model.entity;

import az.enjooy.dto.AnswerDTO;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import static javax.persistence.GenerationType.IDENTITY;

@Data
@NoArgsConstructor
@Entity
public class Answer {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    private String answer;

    public Answer(AnswerDTO answerDTO) {
        this.id = answerDTO.getId();;
        this.answer = answerDTO.getAnswer();
    }
}
