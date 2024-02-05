package com.Apiwiz.taskmanagementapi.service.impl;

import com.Apiwiz.taskmanagementapi.service.MailConfigurationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailConfigurationServiceImpl implements MailConfigurationService {
    @Autowired
    private JavaMailSender mailSender;

    public static String SENDER_EMAIL ="applicationtesting1604@gmail.com";

    public void updateSenderEmail(String senderEmail){
        SENDER_EMAIL=senderEmail;
    }
    public void mailSender(String recipientEmail, String body, String subject){
        SimpleMailMessage mailMessage=new SimpleMailMessage();
        mailMessage.setFrom(SENDER_EMAIL);
        mailMessage.setTo(recipientEmail);
        mailMessage.setSubject(subject);
        mailMessage.setText(body);
        mailSender.send(mailMessage);
    }
}
