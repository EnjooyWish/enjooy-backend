package az.enjooy.dto;

import az.enjooy.model.entity.Question;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class QuestionGetDTO {
    private Long id;
    private String question;
    private Integer questionOrder;

    private List<AnswerDTO> answers = new ArrayList<>();

    public QuestionGetDTO(Question question) {
        this.id = question.getId();
        this.question = question.getQuestion();
        this.questionOrder = question.getQuestionOrder();
        if(question.getAnswers()!=null || question.getAnswers().size()!=0) {
            question.getAnswers()
                    .forEach(x-> answers.add(new AnswerDTO(x)));
        }
    }
}
