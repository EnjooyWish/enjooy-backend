package az.enjooy.model.entity;

import az.enjooy.dto.ClientMessageDTO;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import static javax.persistence.GenerationType.IDENTITY;

@Data
@NoArgsConstructor
@Entity
public class ClientMessage {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    private String fullName;

    private String email;

    private String subject;

    @Column(length = 5000)
    private String text;


    public ClientMessage (ClientMessageDTO clientMessageDTO) {
        this.fullName = clientMessageDTO.getFullName();
        this.email = clientMessageDTO.getEmail();
        this.subject = clientMessageDTO.getSubject();
        this.text = clientMessageDTO.getText();
    }
}
