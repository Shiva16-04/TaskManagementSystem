package com.Apiwiz.taskmanagementapi.transformers;

import com.Apiwiz.taskmanagementapi.dtos.requestdtos.UserRequest;
import com.Apiwiz.taskmanagementapi.dtos.responsedtos.UserResponse;
import com.Apiwiz.taskmanagementapi.enums.Role;
import com.Apiwiz.taskmanagementapi.exceptions.InstanceCreationRestrictedException;
import com.Apiwiz.taskmanagementapi.models.User;
import com.Apiwiz.taskmanagementapi.service.impl.PasswordManagerServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;

public class UserTransformer {

    private UserTransformer(){
        throw new InstanceCreationRestrictedException("Instance of User Transformer cannot be created");
    }
    public static User userRequestToUser(UserRequest userRequest, Role userRole){

        return User.builder()
                .name(userRequest.getName())
                .age(userRequest.getAge())
                .contactNumber(userRequest.getContactNumber())
                .emailId(userRequest.getEmailId())
                .role(userRole)
                .taskList(new ArrayList<>())
                .password(userRequest.getPassword())
                .build();
    }
    public static UserResponse userToUserResponse(User user){
        return UserResponse.builder()
                .name(user.getName())
                .role(user.getRole().toString())
                .age(user.getAge())
                .emailId(user.getEmailId())
                .contactNumber(user.getContactNumber())
                .build();
    }
}
