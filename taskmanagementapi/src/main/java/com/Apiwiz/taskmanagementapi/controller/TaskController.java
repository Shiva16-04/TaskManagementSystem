package com.Apiwiz.taskmanagementapi.controller;

import com.Apiwiz.taskmanagementapi.dtos.requestdtos.TaskRequest;
import com.Apiwiz.taskmanagementapi.enums.Status;
import com.Apiwiz.taskmanagementapi.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("task")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @PostMapping("/add-task")
    public ResponseEntity addTask(@RequestBody TaskRequest taskRequest){
        try {
            return new ResponseEntity<>(taskService.addTask(taskRequest), HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    @PostMapping("/add-user-task-by-admin")
    public ResponseEntity addUserTaskByAdmin(@RequestBody TaskRequest taskRequest,
                                             @RequestParam String emailId){
        try {
            return new ResponseEntity<>(taskService.addTask(taskRequest, emailId), HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/get-task-list")
    public ResponseEntity getFilteredTaskList(@RequestParam(defaultValue = "0") int page,
                                              @RequestParam(defaultValue = "10") int size,
                                              @RequestParam (required = false) String taskCode,
                                              @RequestParam (required = false) String title,
                                              @RequestParam (required = false) LocalDateTime startDueDate,
                                              @RequestParam (required = false) LocalDateTime endDueDate,
                                              @RequestParam (required = false) List<Status> statuses){
        try {
            Pageable pageable = PageRequest.of(page, size);
            return new ResponseEntity<>(taskService.getFilteredTaskResponseList(taskCode, title, startDueDate, endDueDate, statuses), HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("/get-task-list-by-admin")
    public ResponseEntity getFilteredTaskListByAdmin(@RequestParam(defaultValue = "0") int page,
                                                     @RequestParam(defaultValue = "10") int size,
                                                     @RequestParam String userEmailId,
                                                     @RequestParam (required = false) String taskCode,
                                                     @RequestParam(required = false) String title,
                                                     @RequestParam (required = false) LocalDateTime startDueDate,
                                                     @RequestParam (required = false) LocalDateTime endDueDate,
                                                     @RequestParam (required = false) List<Status> statuses
    ){
        try {
            Pageable pageable = PageRequest.of(page, size);
            return new ResponseEntity<>(taskService.getFilteredTaskResponseList(taskCode, userEmailId, title, startDueDate, endDueDate, statuses), HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PatchMapping("/update-task")
    public ResponseEntity updateTask(@RequestParam String taskCode,
                                     @RequestParam (required = false) String title,
                                     @RequestParam (required = false) String description,
                                     @RequestParam (required = false) LocalDateTime dueDate,
                                     @RequestParam (required = false) Status status){
        try {
            return new ResponseEntity<>(taskService.updateTask(taskCode, title, description, dueDate, status), HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    @PatchMapping("/update-user-task-by-admin")
    public ResponseEntity updateTask(@RequestParam String taskCode,
                                     @RequestParam (required = false) String userEmailId,
                                     @RequestParam (required = false) String title,
                                     @RequestParam (required = false) String description,
                                     @RequestParam (required = false) LocalDateTime dueDate,
                                     @RequestParam (required = false) Status status){
        try {
            return new ResponseEntity<>(taskService.updateTask(taskCode, userEmailId, title, description, dueDate, status), HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }


    @DeleteMapping("/delete-tasks")
    public ResponseEntity deleteTasks(@RequestParam List<String> taskCodeList){
        try {
            return new ResponseEntity<>(taskService.deleteTasks(taskCodeList), HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    @DeleteMapping("/delete-user-tasks-by-admin")
    public ResponseEntity deleteTasks(@RequestParam String userEmailId,
                                      @RequestParam List<String> taskCodeList){
        try {
            return new ResponseEntity<>(taskService.deleteTasks(taskCodeList, userEmailId), HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
