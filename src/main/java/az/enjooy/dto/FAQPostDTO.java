package az.enjooy.dto;

import lombok.Data;

@Data
public class FAQPostDTO {
    private Long id;
    private String question;
    private String answer;
    private Boolean isActive;
}
