package com.Apiwiz.taskmanagementapi.transformers;

import com.Apiwiz.taskmanagementapi.dtos.requestdtos.TaskRequest;
import com.Apiwiz.taskmanagementapi.dtos.responsedtos.TaskResponse;
import com.Apiwiz.taskmanagementapi.enums.Status;
import com.Apiwiz.taskmanagementapi.exceptions.InstanceCreationRestrictedException;
import com.Apiwiz.taskmanagementapi.models.Task;

public class TaskTransformer {
    private TaskTransformer(){
        throw new InstanceCreationRestrictedException("Instance cannot be created due to memory constraints");
    }

    public static Task taskRequestToTask(TaskRequest taskRequest){
        return Task.builder()
                .title(taskRequest.getTitle())
                .description(taskRequest.getDescription())
                .dueDate(taskRequest.getDueDate())
                .status(Status.PENDING)
                .build();
    }

    public static TaskResponse taskToTaskResponse(Task task){
        return TaskResponse.builder()
                .taskCode(task.getCode())
                .title(task.getTitle())
                .description(task.getDescription())
                .dueDate(task.getDueDate())
                .status(task.getStatus().toString())
                .userCode(task.getUser().getEmailId())
                .build();
    }
}
