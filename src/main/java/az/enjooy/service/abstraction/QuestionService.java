package az.enjooy.service.abstraction;

import az.enjooy.dto.QuestionGetDTO;
import az.enjooy.dto.QuestionSaveDTO;
import az.enjooy.model.entity.Question;

import java.util.List;

public interface QuestionService {
    Question save(QuestionSaveDTO saveDTO);
    List<QuestionGetDTO> getAll();
}
