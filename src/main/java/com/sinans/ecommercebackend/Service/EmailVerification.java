package com.sinans.ecommercebackend.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

@Service
@RequiredArgsConstructor
public class EmailVerification {

    private final JavaMailSender mailSender;
    private final SpringTemplateEngine templateEngine;

    public void sendVerificationEmail(String to, String username, String token) {
        Context context = new Context();
        context.setVariable("username", username);
        context.setVariable("token", "http://localhost:8080/ecommerce/users/verify?token=" + token);
        String emailContent = templateEngine.process("email/verification", context);
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        try{
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
            helper.setTo(to);
            helper.setSubject("Email Verification");
            helper.setText(emailContent, true);
            mailSender.send(mimeMessage);
        }
        catch (MessagingException e){
            throw new RuntimeException(e);
        }

    }
}
