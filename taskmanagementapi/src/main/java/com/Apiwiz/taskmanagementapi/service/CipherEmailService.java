package com.Apiwiz.taskmanagementapi.service;

import com.Apiwiz.taskmanagementapi.dtos.requestdtos.CipherEmailRequest;
import com.Apiwiz.taskmanagementapi.models.CipherEmail;

public interface CipherEmailService {
    public CipherEmail getCipherEmailByEmailId(String email);
    public void addCipherEmail(CipherEmailRequest cipherEmailRequest);
}
