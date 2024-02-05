package com.Apiwiz.taskmanagementapi.generators;

import org.springframework.stereotype.Component;
import java.util.UUID;

@Component
public class EmailGenerator {

    public String userEmailValidationCodeGenerator(){
        UUID uuid= UUID.randomUUID();
        return ""+uuid;
    }
    public String userSuccessfulRegistrationMessageEmailGenerator(String name){

        return  "Dear "+name+" !!!\n\n\n" +
                "Welcome to the Task Manager Application. You have been successfully registered with us.\n\n\n" +
                "Regards,\n\n" +
                "Team Apiwiz";

    }
}
