package az.enjooy.service.abstraction;

public interface EmailService {
    void send(String to, String email, String status);
    void sendOTPCode(String email, Integer otp);

}
