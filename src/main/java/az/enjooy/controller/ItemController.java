package az.enjooy.controller;

import az.enjooy.dto.ResponseDTO;
import az.enjooy.dto.item.*;
import az.enjooy.model.entity.Item;
import az.enjooy.service.abstraction.ItemRateService;
import az.enjooy.service.abstraction.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("items")
@RequiredArgsConstructor
@CrossOrigin("*")
public class ItemController {

    private final ItemService service;
    private final ItemRateService itemRateService;

    @GetMapping
    public ResponseEntity<ResponseDTO> get(Long categoryId, String name, Double minPrice, Double maxPrice, int page, int size) {
        return new ResponseEntity<>(ResponseDTO.builder()
                .success(true)
                .data(service.getAll(categoryId, name, minPrice, maxPrice, page, size))
                .build(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ResponseDTO> save(@RequestBody ItemSaveDTO saveDTO) {
        System.out.println(saveDTO);
        return new ResponseEntity<>(ResponseDTO.builder()
                .success(true)
                .data(service.save(saveDTO))
                .build(), HttpStatus.OK);
    }

    @GetMapping("/link")
    public ResponseEntity<ResponseDTO> getFromUrl(String url) {
        return new ResponseEntity<>(ResponseDTO.builder()
                .success(true)
                .data(service.getFromUrl(url))
                .build(), HttpStatus.OK);
    }

    @PostMapping("/generator")
    public ResponseEntity<ResponseDTO> getItem(@RequestBody ItemGenerateDTO itemGenerateDTO) {
        return ResponseEntity.ok(itemRateService.generateItemAnswerBased(itemGenerateDTO));
    }

    @PostMapping("/answer-rate")
    public ResponseEntity<ItemAnswerRateDTO> postAnswerRate(@RequestBody ItemAnswerRateDTO itemAnswerRateDTO) {
        return ResponseEntity.ok(itemRateService.save(itemAnswerRateDTO));

    }
}
