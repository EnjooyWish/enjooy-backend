package az.enjooy.service.abstraction;

import az.enjooy.dto.FAQPostDTO;
import az.enjooy.model.entity.FAQ;

import java.util.List;

public interface FAQService {
    List<FAQ> getAllActive();
    FAQ post(FAQPostDTO postDTO);
    void delete(Long id);
}
