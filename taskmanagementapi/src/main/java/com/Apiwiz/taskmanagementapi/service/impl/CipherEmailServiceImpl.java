package com.Apiwiz.taskmanagementapi.service.impl;

import com.Apiwiz.taskmanagementapi.dtos.requestdtos.CipherEmailRequest;
import com.Apiwiz.taskmanagementapi.exceptions.InvalidEmailVerificationCodeException;
import com.Apiwiz.taskmanagementapi.models.CipherEmail;
import com.Apiwiz.taskmanagementapi.repository.CipherEmailRepository;
import com.Apiwiz.taskmanagementapi.service.CipherEmailService;
import com.Apiwiz.taskmanagementapi.transformers.CipherEmailTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CipherEmailServiceImpl implements CipherEmailService {
    @Autowired
    private CipherEmailRepository cipherEmailRepository;

    @Autowired
    private PasswordManagerServiceImpl passwordManagerService;

    public CipherEmail getCipherEmailByEmailId(String email){
        Optional<CipherEmail>optionalCipherEmailDirectory= cipherEmailRepository.findByEmailId(email);
        if(optionalCipherEmailDirectory.isPresent()){
            return optionalCipherEmailDirectory.get();
        }else{
            throw new InvalidEmailVerificationCodeException("Invalid verification code");
        }
    }
    public void addCipherEmail(CipherEmailRequest cipherEmailRequest){

        String email = cipherEmailRequest.getEmail();
        String verificationCode = cipherEmailRequest.getVerificationCode();
        Optional<CipherEmail> optionalCipherEmailDirectory = cipherEmailRepository
                .findByEmailId(email);

        if(optionalCipherEmailDirectory.isPresent()){
            CipherEmail cipherEmail = optionalCipherEmailDirectory.get();
            cipherEmail.setVerificationCode(verificationCode);
            cipherEmailRepository.save(cipherEmail);
        }else {
            CipherEmail cipherEmail = CipherEmailTransformer
                    .cipherEmailRequestToCipherEmail(cipherEmailRequest);

            //saving the entity or new instance
            cipherEmailRepository.save(cipherEmail);
        }
    }
}
