package az.enjooy.service.implementation;

import az.enjooy.dto.item.ItemFromUrlDTO;
import az.enjooy.dto.item.ItemGetDTO;
import az.enjooy.dto.item.ItemSaveDTO;
import az.enjooy.dto.item.ItemSearchDTO;
import az.enjooy.model.entity.Item;
import az.enjooy.repository.ItemRepository;
import az.enjooy.service.abstraction.ConfigService;
import az.enjooy.service.abstraction.ItemCategoryService;
import az.enjooy.service.abstraction.ItemService;
import az.enjooy.util.ListUtil;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {

    private final ItemRepository repository;
    private final ItemCategoryService service;

    @Override
    public Item getByUrl(String url) {
        return repository.findByUrl(url);
    }

    @Override
    public Boolean isItemExistsByUrl(String url) {
        return getAll()
                .stream()
                .map(Item::getUrl)
                .collect(Collectors.toList())
                .contains(url);
    }

    @Override
    public List<Item> getAll() {
        return repository.findAll();
    }

    @Override
    public Item getById(Long id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public Page<ItemGetDTO> getAll(Long categoryId, String name, Double minPrice, Double maxPrice, int page, int size) {
        ItemSearchDTO searchDTO = new ItemSearchDTO(categoryId, name, minPrice, maxPrice, page, size);
        List<Item> cases = repository.search(searchDTO);
        List<ItemGetDTO> casesDTO = new ArrayList<>();
        cases.forEach(x -> casesDTO.add(new ItemGetDTO(x)));
        return ListUtil.convertToPage(casesDTO, searchDTO.getSize(), searchDTO.getPage());
    }

    @Override
    public Item save(ItemSaveDTO saveDTO) {
        return isItemExistsByUrl(saveDTO.getUrl()) ? null : repository.save(new Item(saveDTO, service.getById(saveDTO.getCategoryId()), true));
    }

    @Override
    public Item save(Item item) {
        System.out.println(item);
        return isItemExistsByUrl(item.getUrl())
                ? getByUrl(item.getUrl())
                : repository.save(item);
    }

    @Override
    public ItemFromUrlDTO getFromUrl(String url) {

        String json = Jsoup.parse(readURL(url)).toString();
        String name = (StringUtils.substringsBetween(json, "<title>", "</title>"))[0];
        String photoUrl = (StringUtils.substringsBetween(json, "\"og:image\" content=\"", "\">"))[0];
        String description = (StringUtils.substringsBetween(json, "\"og:description\" content=\"", "\">"))[0];
//        String name = (StringUtils.substringsBetween(json, ",\"name\":\"", "\""))[0];
//        String photoUrl = (StringUtils.substringsBetween(json, ",\"image\":\"", "\""))[0];
        //String[] priceArray = StringUtils.substringsBetween(json, ",\"price\":", ",");
        //Double price = null;
        //if (priceArray != null)
        //    price = Double.parseDouble((StringUtils.substringsBetween(json, ",\"price\":", ","))[0]);
        //if (price == null)
        //    price = Double.parseDouble((StringUtils.substringsBetween(json, ",\"lowPrice\":", ","))[0]);

//        Item itemInDb = repository.findByUrl(url);
//        if(itemInDb !=null && itemInDb.getPrice().doubleValue()==price.doubleValue())
//            return null;
//        if(itemInDb !=null && itemInDb.getPrice().doubleValue()!=price.doubleValue()) {
//            itemInDb.setPrice(price);
//            return repository.save(itemInDb);
//        }
//        return repository.save(new Item(name, price, url, photoUrl));

        return ItemFromUrlDTO.builder()
                .url(url)
                .name(name)
                .photoUrl(photoUrl)
                .description(description)
                //.price(price)
                .build();
    }

    public String readURL(String url) {

        String fileContents = "";
        String currentLine = "";

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(new URL(url).openStream()));
            fileContents = reader.readLine();
            while (currentLine != null) {
                currentLine = reader.readLine();
                fileContents += "\n" + currentLine;
            }
            reader.close();
            reader = null;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error Message", JOptionPane.OK_OPTION);
            e.printStackTrace();

        }

        return fileContents;
    }
}
