package com.example.bookstore.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;


@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendSimpleEmail(String toEmail,
                                String subject,
                                String body) {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("ankitrai3453@gmail.com");  // must be same as spring.mail.username
        message.setTo(toEmail);
        message.setSubject(subject);
        message.setText(body);

        mailSender.send(message);

        Logger log = LoggerFactory.getLogger(this.getClass());
        log.info("Mail Sent Successfully...");
    }
}
