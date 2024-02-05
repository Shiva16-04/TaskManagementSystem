package com.Apiwiz.taskmanagementapi.dtos.responsedtos;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserResponse {
    String name;
    String role;
    int age;
    String emailId;
    String contactNumber;
}
