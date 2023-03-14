package az.enjooy.service.implementation;

import az.enjooy.dto.ClientMessageDTO;
import az.enjooy.model.entity.ClientMessage;
import az.enjooy.repository.ClientMessageRepository;
import az.enjooy.service.abstraction.ClientMessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClientMessageServiceImpl implements ClientMessageService {

    private final ClientMessageRepository repository;

    @Override
    public List<ClientMessage> findAll() {
        return repository.findAll();
    }

    @Override
    public ClientMessage save(ClientMessageDTO messageDTO) {
        return repository.save(new ClientMessage(messageDTO));
    }
}
