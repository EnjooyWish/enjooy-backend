package az.enjooy.service.implementation;

import az.enjooy.dto.QuestionGetDTO;
import az.enjooy.dto.ResponseDTO;
import az.enjooy.dto.item.ItemAnswerRateDTO;
import az.enjooy.dto.item.ItemGenerateDTO;
import az.enjooy.dto.item.ItemGetDTO;
import az.enjooy.dto.item.ItemRateDTO;
import az.enjooy.model.entity.*;
import az.enjooy.repository.ConfigRepository;
import az.enjooy.repository.ItemRateRepository;
import az.enjooy.service.abstraction.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ItemRateServiceImpl implements ItemRateService {

    private final ItemRateRepository repository;
    private final UserServiceImpl userService;
    private final UserRecommendService userRecommendService;
    private final ItemService itemService;
    private final QuestionService questionService;

    private final ConfigRepository configRepository;
    private static final String type = "gift-recommend";

    @Override
    public List<ItemAnswerRateDTO> getAll() {
        List<QuestionGetDTO> questions = questionService.getAll();
        return null;
    }

    @Override
    public ItemAnswerRateDTO save(ItemAnswerRateDTO itemAnswerRateDTO) {
        Item item = itemService.getById(itemAnswerRateDTO.getItemId());
        Answer answer = new Answer(itemAnswerRateDTO.getAnswerDTO());
        Integer rate = itemAnswerRateDTO.getRate();
        ItemRate itemRateDB = repository.findByAnswerAndItem(answer, item);
        if(itemRateDB!=null) {
            itemRateDB.setRate(rate);
            repository.save(itemRateDB);
            itemAnswerRateDTO.setId(itemRateDB.getId());
        }
        else {
            ItemRate itemRate = repository.save(ItemRate.builder()
                    .item(item)
                    .answer(answer)
                    .rate(rate)
                    .build());
            itemAnswerRateDTO.setId(itemRate.getId());
        }
        return itemAnswerRateDTO;
    }

    @Override
    public ResponseDTO generateItemAnswerBased(ItemGenerateDTO itemGenerateDTO) {
        List<ItemGetDTO> response = new ArrayList<>();
        List<ItemRateDTO> itemRates = repository.generateItemAnswerBased(itemGenerateDTO.getAnswerIds());
        if (itemRates != null) {
            itemRates = itemRates.stream().sorted(Comparator.comparing(ItemRateDTO::getRateSum).reversed()).limit(3).collect(Collectors.toList());
            List<Item> items = itemRates.stream().map(ItemRateDTO::getItem).collect(Collectors.toList());
            items.forEach(x -> response.add(new ItemGetDTO(x)));
            if (itemGenerateDTO.getUsername() != null) {
                User user = userService.findByUsername(itemGenerateDTO.getUsername());
                userRecommendService.save(UserRecommend.builder()
                        .user(user)
                        .items(items)
                        .build());
            }
            Config config = configRepository.getAllByType(type).get(0);
            return ResponseDTO.builder()
                    .success(true)
                    .title(config.getName())
                    .subtitle(config.getValue())
                    .data(response)
                    .build();
        } else
            return null;
    }
}
