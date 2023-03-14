package az.enjooy.dto.item;

import az.enjooy.dto.AnswerDTO;
import lombok.Data;

@Data
public class ItemAnswerRateDTO {
    private Long id;
    private Long itemId;

    private String question;
    private AnswerDTO answerDTO;
    private Integer rate;

}
