package com.Apiwiz.taskmanagementapi.dtos.responsedtos;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TaskResponse {
    String taskCode;
    String title;
    String description;
    LocalDateTime dueDate;
    String status;
    String userCode;
}
