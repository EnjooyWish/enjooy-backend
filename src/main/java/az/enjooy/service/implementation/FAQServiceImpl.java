package az.enjooy.service.implementation;

import az.enjooy.dto.FAQPostDTO;
import az.enjooy.model.entity.FAQ;
import az.enjooy.repository.FAQRepository;
import az.enjooy.service.abstraction.FAQService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FAQServiceImpl implements FAQService {

    private final FAQRepository repository;

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Override
    public FAQ post(FAQPostDTO postDTO) {
        return repository.save(new FAQ(postDTO));
    }

    @Override
    public List<FAQ> getAllActive() {
        return repository.getAllByIsActive(true);
    }
}
