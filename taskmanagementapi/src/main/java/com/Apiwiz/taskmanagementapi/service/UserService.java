package com.Apiwiz.taskmanagementapi.service;

import com.Apiwiz.taskmanagementapi.dtos.requestdtos.EmailRequest;
import com.Apiwiz.taskmanagementapi.dtos.requestdtos.UserRequest;
import com.Apiwiz.taskmanagementapi.enums.Role;
import com.Apiwiz.taskmanagementapi.models.User;

public interface UserService {
    public String addUser(UserRequest userRequest, Role role);
    public String emailVerificationCodeSender(EmailRequest emailRequest);
    public User getUserByEmailId(String emailId);
}
