package az.enjooy.model;

import az.enjooy.model.entity.User;
import lombok.Data;

@Data
public class LightUser {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;

    public LightUser(User user) {
        this.id = user.getId();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.email = user.getEmail();
    }
}
