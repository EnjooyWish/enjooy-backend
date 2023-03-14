package az.enjooy.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
public class ResponseDTO {
    Boolean success;
    String title;
    String subtitle;
    Object data;

    public ResponseDTO(Object data) {
        this.success = true;
        this.data = data;
    }

    public ResponseDTO() {
        this.success = true;
    }
}

