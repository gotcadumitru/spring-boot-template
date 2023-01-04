package com.dima.demo.email;

import com.dima.demo.exception.ApiRequestException;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class EmailService implements EmailSender {

    private final JavaMailSender javaMailSender;

    private final static Logger LOGGER = LoggerFactory.getLogger(EmailService.class);


    @Async
    public void send(String to,String subject, String body) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage,"utf-8");
        try {
            mimeMessageHelper.setText(body,true);
            mimeMessageHelper.setTo(to);
            mimeMessageHelper.setSubject(subject);
            mimeMessageHelper.setFrom("dumitru.gotca.dev@gmail.com");
            javaMailSender.send(mimeMessage);
        } catch (MessagingException e) {
            LOGGER.error("Failed to send message",e);
            throw new ApiRequestException("Failed to send message");
        }
    }

    @Override
    public void send(String to, String email) {
        send(to,"Email confirmation",email);
    }

}
