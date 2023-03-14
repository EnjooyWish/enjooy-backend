package az.enjooy.controller;

import az.enjooy.dto.ItemCategory.ItemCategorySaveDTO;
import az.enjooy.dto.ResponseDTO;
import az.enjooy.service.abstraction.ItemCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("item-categories")
@RequiredArgsConstructor
@CrossOrigin("*")
public class ItemCategoryController {

    private final ItemCategoryService service;


    @GetMapping
    public ResponseEntity<ResponseDTO> getAll() {
        return new ResponseEntity<>(ResponseDTO.builder()
                .success(true)
                .data(service.getAll())
                .build(), HttpStatus.OK);
    }

    @GetMapping("/parents")
    public ResponseEntity<ResponseDTO> getAllParents(int page, int size) {
        return new ResponseEntity<>(ResponseDTO.builder()
                .success(true)
                .data(service.getAllParents(page, size))
                .build(), HttpStatus.OK);
    }


    @PostMapping
    public ResponseEntity<ResponseDTO> save(@RequestBody ItemCategorySaveDTO saveDTO) {
        return new ResponseEntity<>(ResponseDTO.builder()
                .success(true)
                .data(service.save(saveDTO))
                .build(), HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<ResponseDTO> deleteById(@PathVariable Long id) {
        service.delete(id);
        return new ResponseEntity<>(ResponseDTO.builder()
                .success(true)
                .build(), HttpStatus.OK);
    }
}
