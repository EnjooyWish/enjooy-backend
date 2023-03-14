package az.enjooy.dto.authentication;

import lombok.Data;

@Data
public class OTPAuthDTO {
    String email;
    Integer otp;
}
