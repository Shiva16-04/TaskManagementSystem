package com.Apiwiz.taskmanagementapi.dtos.requestdtos;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserRequest {
    String name;
    int age;
    String emailId;
    String emailVerificationCode;
    String contactNumber;
    String password;
    String reTypePassword;
}
