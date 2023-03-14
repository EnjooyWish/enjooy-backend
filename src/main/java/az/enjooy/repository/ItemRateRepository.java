package az.enjooy.repository;

import az.enjooy.dto.item.ItemRateDTO;
import az.enjooy.model.entity.Answer;
import az.enjooy.model.entity.Item;
import az.enjooy.model.entity.ItemRate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ItemRateRepository extends JpaRepository<ItemRate, Long> {

    @Query("SELECT new az.enjooy.dto.item.ItemRateDTO(SUM (ir.rate), ir.item) from ItemRate ir where ir.answer.id in :answerIds group by ir.item")
    List<ItemRateDTO> generateItemAnswerBased(List<Long> answerIds);
    ItemRate findByAnswerAndItem(Answer answer, Item item);
}
