package com.Apiwiz.taskmanagementapi.transformers;

import com.Apiwiz.taskmanagementapi.dtos.requestdtos.CipherEmailRequest;
import com.Apiwiz.taskmanagementapi.exceptions.InstanceCreationRestrictedException;
import com.Apiwiz.taskmanagementapi.models.CipherEmail;
import com.Apiwiz.taskmanagementapi.service.impl.PasswordManagerServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class CipherEmailTransformer {

    private CipherEmailTransformer(){
        throw new InstanceCreationRestrictedException("Instance creation of CipherEmailTransformer class " +
                "is restricted due to memory obligations");
    }
    public static CipherEmail cipherEmailRequestToCipherEmail(CipherEmailRequest
                                                                      cipherEmailRequest){
        //using BCrypt Encoder for verification code encoding
        return CipherEmail.builder()
                .emailId(cipherEmailRequest.getEmail())
                .verificationCode(cipherEmailRequest.getVerificationCode())
                .build();
    }
}
