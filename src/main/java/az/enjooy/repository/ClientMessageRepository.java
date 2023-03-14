package az.enjooy.repository;

import az.enjooy.model.entity.ClientMessage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientMessageRepository extends JpaRepository<ClientMessage, Long> {
}
