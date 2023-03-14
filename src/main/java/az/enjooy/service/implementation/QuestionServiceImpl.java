package az.enjooy.service.implementation;

import az.enjooy.dto.QuestionGetDTO;
import az.enjooy.dto.QuestionSaveDTO;
import az.enjooy.model.entity.Question;
import az.enjooy.repository.QuestionRepository;
import az.enjooy.service.abstraction.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class QuestionServiceImpl implements QuestionService {

    private final QuestionRepository repository;

    @Override
    public List<QuestionGetDTO> getAll() {
        List<QuestionGetDTO> questions = new ArrayList<>();
        repository.findAll().forEach(x->questions.add(new QuestionGetDTO(x)));
        return questions;
    }

    @Override
    public Question save(QuestionSaveDTO saveDTO) {
        return repository.save(new Question(saveDTO));
    }
}
