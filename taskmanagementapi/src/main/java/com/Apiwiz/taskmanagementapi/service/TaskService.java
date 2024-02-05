package com.Apiwiz.taskmanagementapi.service;

import com.Apiwiz.taskmanagementapi.dtos.requestdtos.TaskRequest;
import com.Apiwiz.taskmanagementapi.dtos.responsedtos.TaskResponse;
import com.Apiwiz.taskmanagementapi.enums.Status;
import com.Apiwiz.taskmanagementapi.models.Task;
import com.Apiwiz.taskmanagementapi.models.User;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface TaskService {
    public String addTask(TaskRequest taskRequest);
    public String addTask(TaskRequest taskRequest, String userEmailId);
    private String taskAdditionProcessor(User user, TaskRequest taskRequest) {
        return null;
    }

    public List<TaskResponse> getFilteredTaskResponseList(String taskCode, String title, LocalDateTime startDueDate,
                                                          LocalDateTime endDueDate, List<Status> statuses);
    public List<TaskResponse> getFilteredTaskResponseList(String taskCode, String emailId, String title, LocalDateTime startDueDate,
                                                          LocalDateTime endDueDate, List<Status> statuses);
    private List<Task> getFilteredTaskList(String taskCode, String userEmailId, String title, LocalDateTime startDueDate,
                                           LocalDateTime endDueDate, List<Status> statuses) {
        return null;
    }

    public String updateTask(String taskCode, String title, String description, LocalDateTime dueDate, Status status);
    public String updateTask(String taskCode, String userEmailId, String title, String description, LocalDateTime dueDate, Status status);
    private String taskUpdationProcessor(String taskCode, User user, String title, String description, LocalDateTime dueDate, Status status){
        return null;
    }

    public String deleteTasks(List<String> taskCodeList);
    public String deleteTasks(List<String> taskCodeList, String userEmailId);
    private boolean taskDeletionProcessor(User user, String taskCode){
        return true;
    }
}
