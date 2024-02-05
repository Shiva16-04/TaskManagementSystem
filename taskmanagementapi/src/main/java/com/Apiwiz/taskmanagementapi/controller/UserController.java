package com.Apiwiz.taskmanagementapi.controller;

import com.Apiwiz.taskmanagementapi.dtos.requestdtos.EmailRequest;
import com.Apiwiz.taskmanagementapi.dtos.requestdtos.UserRequest;
import com.Apiwiz.taskmanagementapi.enums.Role;
import com.Apiwiz.taskmanagementapi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("user")
public class UserController {
    @Autowired
    private UserService userService;
    @PostMapping("/email-authentication-code-to-user-email")
    public ResponseEntity sendEmailValidationCode(@RequestBody EmailRequest emailRequest){
        try {
            return new ResponseEntity<>(userService.emailVerificationCodeSender(emailRequest), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/add-user")
    public ResponseEntity addUser(@RequestBody UserRequest userRequest){
        try {
            return new ResponseEntity<>(userService.addUser(userRequest, Role.USER), HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    @PostMapping("/add-admin-user")
    public ResponseEntity addAdminUser(@RequestBody UserRequest userRequest){
        try {
            return new ResponseEntity<>(userService.addUser(userRequest, Role.ADMIN), HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
