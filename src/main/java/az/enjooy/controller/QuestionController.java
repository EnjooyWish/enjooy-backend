package az.enjooy.controller;

import az.enjooy.dto.QuestionSaveDTO;
import az.enjooy.dto.ResponseDTO;
import az.enjooy.model.entity.Question;
import az.enjooy.service.abstraction.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("questions")
@RequiredArgsConstructor
@CrossOrigin("*")
public class QuestionController {

    private final QuestionService service;

    @GetMapping
    public ResponseEntity<ResponseDTO> get() {
        return new ResponseEntity<>(ResponseDTO.builder()
                .success(true)
                .data(service.getAll())
                .build(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ResponseDTO> post(@RequestBody QuestionSaveDTO saveDTO) {
        return new ResponseEntity<>(ResponseDTO.builder()
                .success(true)
                .data(service.save(saveDTO))
                .build(), HttpStatus.OK);
    }
}
