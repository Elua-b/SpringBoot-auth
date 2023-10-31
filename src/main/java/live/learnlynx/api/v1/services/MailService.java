package live.learnlynx.api.v1.services;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MailService {

    @Autowired
    private JavaMailSender mailSender;

    private final SimpleMailMessage message = new SimpleMailMessage();

    public void sendResetPasswordMail(String toEmail, String names, String activationCodes) {
        message.setFrom("premugisha64@gmail.com");
        message.setTo(toEmail);
        message.setText("Dear " + names + "!\n" +
                "\n" +
                "You've requested to reset password to learn-lynx, " +
                "your verification code is " + activationCodes + ". \n" +
                "\n" +
                "This code expires in 5 minutes.\n" +
                "\n" +
                "If you have any questions, send us an email learnlynx@support.com.\n" +
                "\n" +
                "We’re glad you’re here!\n" +
                "\n");
        message.setSubject("Learn Lynx Password Reset Code");
        mailSender.send(message);
    }

    public void sendVerificationMail(String toEmail, String names, String verificationCode) {
        message.setFrom("premugisha64@gmail.com");
        message.setTo(toEmail);
        message.setText("Dear " + names + "!\n" +
                "\n" +
                "Here is your account verification code, " +
                "your account verification code is " + verificationCode + ". \n" +
                "\n" +
                "This code expires in 1 hour.\n" +
                "\n" +
                "If you have any questions, send us an email divin@support.com.\n" +
                "\n" +
                "We’re glad you’re here!\n" +
                "\n");
        message.setSubject("Learn Lynx Verification code");
        mailSender.send(message);
    }
}