package az.enjooy.service.abstraction;

import az.enjooy.model.entity.OTPCode;

public interface OTPCodeService {
    OTPCode save(OTPCode otpCode);

    void sendOtpCode(String email);

    void handleUserActiveOTPs(String email);

    Boolean checkOTPCode(String email, Integer otpCode);

}
