package az.enjooy.controller;

import az.enjooy.dto.ConfigurationPostDTO;
import az.enjooy.dto.ResponseDTO;
import az.enjooy.service.abstraction.ConfigService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("objects")
@CrossOrigin("*")
public class ConfigurationController {

    private final ConfigService service;

    @GetMapping
    public ResponseEntity<ResponseDTO> getAll() {
        return new ResponseEntity<>(service.findAll(), HttpStatus.OK);
    }

    @GetMapping(params = "id")
    public ResponseEntity<ResponseDTO> getById( Long id) {
        return new ResponseEntity<>(service.findById(id), HttpStatus.OK);
    }

    @GetMapping(params = "name")
    public ResponseEntity<ResponseDTO> getById(String name) {
        return new ResponseEntity<>(service.findByName(name), HttpStatus.OK);
    }

    @GetMapping(params = "type")
    public ResponseEntity<ResponseDTO> getAllByType(String type) {
        return new ResponseEntity<>(service.getAllByType(type), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ResponseDTO> post(@RequestBody ConfigurationPostDTO configurationPostDTO) {
        return new ResponseEntity<>(service.save(configurationPostDTO), HttpStatus.OK);
    }
}
