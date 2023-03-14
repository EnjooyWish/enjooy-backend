package az.enjooy.service.abstraction;

import az.enjooy.dto.ClientMessageDTO;
import az.enjooy.model.entity.ClientMessage;

import java.util.List;

public interface ClientMessageService {
    ClientMessage save(ClientMessageDTO messageDTO);
    List<ClientMessage> findAll();
}
