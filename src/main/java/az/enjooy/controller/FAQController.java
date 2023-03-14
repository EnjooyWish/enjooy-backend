package az.enjooy.controller;

import az.enjooy.dto.FAQPostDTO;
import az.enjooy.dto.ResponseDTO;
import az.enjooy.service.abstraction.FAQService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("faqs")
@CrossOrigin("*")
public class FAQController {

    private final FAQService service;

    @GetMapping
    public ResponseEntity<ResponseDTO> getAllActive() {
        return new ResponseEntity<>(ResponseDTO.builder()
                .success(true)
                .data(service.getAllActive())
                .build(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ResponseDTO> post(@RequestBody FAQPostDTO postDTO) {
        return new ResponseEntity<>(ResponseDTO.builder()
                .success(true)
                .data(service.post(postDTO))
                .build(), HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<ResponseDTO> delete(@PathVariable Long id) {
        service.delete(id);
        return new ResponseEntity<>(ResponseDTO.builder()
                .success(true)
                .build(), HttpStatus.OK);
    }

}
