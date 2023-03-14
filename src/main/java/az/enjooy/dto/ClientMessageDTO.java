package az.enjooy.dto;

import lombok.Data;


@Data
public class ClientMessageDTO {

    String fullName;

    String email;

    String subject;

    String text;
}
