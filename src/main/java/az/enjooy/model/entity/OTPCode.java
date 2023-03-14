package az.enjooy.model.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Entity
public class OTPCode {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer otp;

    private String email;

    private LocalDateTime expiresAt;

    private Boolean isActive;

    public OTPCode(Integer otp, String email) {
        this.otp = otp;
        this.email = email;
        this.expiresAt = LocalDateTime.now().plusMinutes(1);
        this.isActive = true;
    }
}
