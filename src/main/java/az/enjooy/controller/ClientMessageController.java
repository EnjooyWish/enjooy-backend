package az.enjooy.controller;

import az.enjooy.dto.ClientMessageDTO;
import az.enjooy.model.entity.ClientMessage;
import az.enjooy.service.abstraction.ClientMessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("client-messages")
@RequiredArgsConstructor
@CrossOrigin("*")
public class ClientMessageController {

    private final ClientMessageService service;

    @GetMapping
    public ResponseEntity<List<ClientMessage>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @PostMapping
    public ResponseEntity<ClientMessage> post(@RequestBody ClientMessageDTO message) {
        return ResponseEntity.ok(service.save(message));
    }
}
