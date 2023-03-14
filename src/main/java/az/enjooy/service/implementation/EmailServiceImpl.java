package az.enjooy.service.implementation;

import az.enjooy.service.abstraction.ConfigService;
import az.enjooy.service.abstraction.EmailService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    private final static Logger LOGGER = LoggerFactory.getLogger(EmailServiceImpl.class);
    private final JavaMailSender mailSender;

    @Override
    @Async
    public void sendOTPCode(String email, Integer otp) {
        send(email, "Your OTP: "+ otp, "OTP");
    }

    @Override
    @Async
    public void send(String to, String email, String status) {
        try{
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
            helper.setText(email, true);
            helper.setTo(to);
            if(status.equals("email")) helper.setSubject("Confirm your email");
            if(status.equals("password")) helper.setSubject("Reset your password");
            if(status.equals("OTP")) helper.setSubject("Your reset password OTP code");
            helper.setFrom("gettourbot@gmail.com");
            mailSender.send(mimeMessage);
        }catch (MessagingException e){
            LOGGER.error("Failed to send an email", e);
            throw new IllegalStateException("Failed to send an email");
        }
    }
}
