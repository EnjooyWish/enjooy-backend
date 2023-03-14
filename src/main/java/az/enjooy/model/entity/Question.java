package az.enjooy.model.entity;

import az.enjooy.dto.QuestionSaveDTO;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;

import static javax.persistence.GenerationType.IDENTITY;

@Data
@NoArgsConstructor
@Entity
public class Question {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    private String question;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Answer> answers = new ArrayList<>();

    private Integer questionOrder;

    public Question(QuestionSaveDTO saveDTO) {
        this.id = saveDTO.getId();
        this.question = saveDTO.getQuestion();
        this.answers.addAll(saveDTO.getAnswers());
        this.questionOrder = saveDTO.getQuestionOrder();
    }
}
