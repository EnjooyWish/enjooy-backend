package az.enjooy.util;

import lombok.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Builder
@Data
@NoArgsConstructor
public class GenericResponse {
    private String message;
    private List<String> errors = new ArrayList<>();

    public GenericResponse(String message, List errors) {
        super();
        this.message = message;
        this.errors = errors;
    }

    public GenericResponse(String message, String error) {
        super();
        this.message = message;
        this.errors = Arrays.asList(error);
    }

}

