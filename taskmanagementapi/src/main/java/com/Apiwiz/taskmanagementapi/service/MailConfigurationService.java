package com.Apiwiz.taskmanagementapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

public interface MailConfigurationService {

    public void updateSenderEmail(String senderEmail);
    public void mailSender(String recipientEmail, String body, String subject);
}
