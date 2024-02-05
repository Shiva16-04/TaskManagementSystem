package com.Apiwiz.taskmanagementapi.dtos.requestdtos;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EmailRequest {
    String Email;
}
