package az.enjooy.service.abstraction;

import az.enjooy.dto.ResponseDTO;
import az.enjooy.dto.item.ItemAnswerRateDTO;
import az.enjooy.dto.item.ItemGenerateDTO;
import az.enjooy.dto.item.ItemGetDTO;
import az.enjooy.dto.item.ItemRateDTO;

import java.util.List;

public interface ItemRateService {
    ResponseDTO generateItemAnswerBased(ItemGenerateDTO itemGenerateDTO);
    ItemAnswerRateDTO save(ItemAnswerRateDTO itemAnswerRateDTO);

    List<ItemAnswerRateDTO> getAll();

}
