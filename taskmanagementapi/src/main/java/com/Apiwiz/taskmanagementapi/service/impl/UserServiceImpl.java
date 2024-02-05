package com.Apiwiz.taskmanagementapi.service.impl;

import com.Apiwiz.taskmanagementapi.dtos.requestdtos.CipherEmailRequest;
import com.Apiwiz.taskmanagementapi.dtos.requestdtos.EmailRequest;
import com.Apiwiz.taskmanagementapi.dtos.requestdtos.UserRequest;
import com.Apiwiz.taskmanagementapi.enums.Role;
import com.Apiwiz.taskmanagementapi.exceptions.*;
import com.Apiwiz.taskmanagementapi.generators.EmailGenerator;
import com.Apiwiz.taskmanagementapi.models.User;
import com.Apiwiz.taskmanagementapi.repository.CipherEmailRepository;
import com.Apiwiz.taskmanagementapi.repository.UserRepository;
import com.Apiwiz.taskmanagementapi.service.MailConfigurationService;
import com.Apiwiz.taskmanagementapi.service.CipherEmailService;
import com.Apiwiz.taskmanagementapi.service.UserService;
import com.Apiwiz.taskmanagementapi.transformers.UserTransformer;
import jakarta.validation.constraints.Pattern;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;



@Slf4j
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private MailConfigurationService mailConfigurationService;
    @Autowired
    private PasswordManagerServiceImpl passwordManagerService;
    @Autowired
    private CipherEmailService cipherEmailService;
    @Autowired
    private CipherEmailRepository cipherEmailRepository;
    @Autowired
    private EmailGenerator emailGenerator;
    @Override
    public String addUser(UserRequest userRequest, Role role) {
        Optional<User>optionalUser=userRepository.findByEmailId(userRequest.getEmailId());
        if(optionalUser.isPresent()){
            log.warn("user already present"+ optionalUser.toString());
            throw new UserAlreadyPresentException("User, with the particular email id "+userRequest.getEmailId()+" already " +
                    "registered. Try logging in by entering the respective credentials");
        }

        //validating the match of password and retype password
        if(!userRequest.getPassword().equals(userRequest.getReTypePassword())){
            throw new PasswordReTypePasswordNotMatchException("Password and reType password did not match");
        }

        //validating the email of the user
        String codeFromUser=userRequest.getEmailVerificationCode();
        emailCodeValidator(userRequest.getEmailId(), codeFromUser);

        //generating user from user request and calling the method to encode the password with BCrypt Encryptor
        userRequest.setPassword(passwordManagerService.encode(userRequest.getPassword()));
        User user= UserTransformer.userRequestToUser(userRequest, role);

        user=userRepository.save(user);
        log.debug(user.toString());

        //sending registration confirmation mail to the user
        String emailBody=emailGenerator.userSuccessfulRegistrationMessageEmailGenerator(user.getName());
        mailConfigurationService.mailSender(user.getEmailId(), emailBody, "User Registration Confirmation");

        return "User "+user.getName()+" has been registered successfully";
    }
    public String emailVerificationCodeSender(EmailRequest emailRequest){
        //for encoding the verification code
        String senderEmail= emailRequest.getEmail();
        String verificationCode=emailGenerator.userEmailValidationCodeGenerator();

        //adding email and verification code to the database
        //calling the method to encode the email validation code with BCrypt Encryptor
        CipherEmailRequest cipherEmailRequest =new CipherEmailRequest(senderEmail, passwordManagerService.encode(verificationCode));
        cipherEmailService.addCipherEmail(cipherEmailRequest);

        //sending the email validator code to the request email
        mailConfigurationService.mailSender(senderEmail, verificationCode, "Email Validation Code");
        return "Verification code sent successfully to the mail"+senderEmail;
    }
    //below methods are used for internal purposes...not for api calling
    private boolean emailCodeValidator(String email, String code){


        String verificationCode= cipherEmailService.getCipherEmailByEmailId(email).getVerificationCode();

        //email verification my comparing sent code and stored encrypted database code
        if(passwordManagerService.matches(code, verificationCode))return true;
        else throw new InvalidEmailVerificationCodeException("Invalid Code");
    }
    private boolean passwordFormatValidator(String password){
        String regex="^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";
        if(password.matches(regex)){
            return true;
        }else{
            throw new PasswordMinimumRequirementNotMatchException("Password should have minimum length of 8 and should " +
                    "contain at least 1 uppercase, 1 lowercase and 1 special character");
        }
    }
    public User getUserByEmailId(String emailId){
        Optional<User>optionalUser=userRepository.findByEmailId(emailId);
        if(!optionalUser.isPresent()){
            throw new UserNotFoundException("Invalid Credentials");
        }
        return optionalUser.get();
    }
}
