package com.Apiwiz.taskmanagementapi.service.impl;

import com.Apiwiz.taskmanagementapi.dtos.requestdtos.TaskRequest;
import com.Apiwiz.taskmanagementapi.dtos.responsedtos.TaskResponse;
import com.Apiwiz.taskmanagementapi.enums.Role;
import com.Apiwiz.taskmanagementapi.enums.Status;
import com.Apiwiz.taskmanagementapi.exceptions.AccessDeniedException;
import com.Apiwiz.taskmanagementapi.exceptions.InvalidTaskCodeException;
import com.Apiwiz.taskmanagementapi.generators.TaskCodeGenerator;
import com.Apiwiz.taskmanagementapi.models.Task;
import com.Apiwiz.taskmanagementapi.models.User;
import com.Apiwiz.taskmanagementapi.repository.TaskRepository;
import com.Apiwiz.taskmanagementapi.service.AuthenticationDetailsService;
import com.Apiwiz.taskmanagementapi.service.TaskService;
import com.Apiwiz.taskmanagementapi.service.UserService;
import com.Apiwiz.taskmanagementapi.transformers.TaskTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskServiceImpl implements TaskService {
    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private TaskCodeGenerator taskCodeGenerator;
    @Autowired
    private AuthenticationDetailsService authenticationDetailsService;

                                          //Task Addition Methods
    @Override
    public String addTask(TaskRequest taskRequest) {

        //Getting the user details from login information and mapping ticket and user
        String emailId=authenticationDetailsService.getAuthenticationDetails();
        User user=userService.getUserByEmailId(emailId);

        return taskAdditionProcessor(user, taskRequest);
    }
    @Override
    public String addTask(TaskRequest taskRequest, String userEmailId){
        //Getting the user details from login information and mapping ticket and user
        String emailId=authenticationDetailsService.getAuthenticationDetails();
        User adminUser=userService.getUserByEmailId(emailId);

        if(adminUser.getRole().equals(Role.USER)){
            throw new AccessDeniedException("Access denied. Only Admins have access to user profiles to add tasks");
        }

        User user=userService.getUserByEmailId(userEmailId);
        return taskAdditionProcessor(user, taskRequest);
    }
    private String taskAdditionProcessor(User user, TaskRequest taskRequest){

        //created task from task request by calling transformer
        Task task= TaskTransformer.taskRequestToTask(taskRequest);

        String taskCode=taskCodeGenerator.generate();

        //setting the attributes
        task.setCode(taskCode);
        task.setUser(user);

        //mapping bidirectionally
        user.getTaskList().add(task);

        //saving it to repository which auto cascades save from task to user
        taskRepository.save(task);

        return "Task "+task.getTitle()+" has been created successfully";
    }
                                        //Task Filtering Methods

    //this method is used to filter task list by user and admin
    public List<TaskResponse> getFilteredTaskResponseList(String taskCode, String title, LocalDateTime startDueDate,
                                                          LocalDateTime endDueDate, List<Status> statuses){
        //Getting the user details from login information and mapping ticket and user
        String emailId=authenticationDetailsService.getAuthenticationDetails();

        List<Task>taskList=getFilteredTaskList(taskCode, emailId, title, startDueDate, endDueDate, statuses);
        return taskToTaskResponseConverter(taskList);
    }
    //this method is exclusively for admin to get filtered response of any user
    public List<TaskResponse> getFilteredTaskResponseList(String taskCode, String emailId, String title, LocalDateTime startDueDate,
                                                          LocalDateTime endDueDate, List<Status> statuses){

        List<Task>taskList=getFilteredTaskList(taskCode, emailId, title, startDueDate, endDueDate, statuses);
        return taskToTaskResponseConverter(taskList);
    }
                                      //internal private methods which processes filtered task list
    private List<Task> getFilteredTaskList(String taskCode, String userEmailId, String title, LocalDateTime startDueDate,
                                                  LocalDateTime endDueDate, List<Status> statuses) {

        List<String>statusList=null;
        if(statuses==null || statuses.size()==0){
            statusList=getStatusStringList();
        }else{
            for(Status status: statuses){
                statusList.add(status.toString());
            }
        }

        List<Task>taskList=taskRepository.findFilteredTaskList(taskCode, userEmailId, title, startDueDate, endDueDate, statusList);
        return taskList;
    }
    private List<String> getStatusStringList(){
        return Arrays.stream(Status.values())
                .map(Enum::toString)
                .collect(Collectors.toList());
    }

    private List<TaskResponse> taskToTaskResponseConverter(List<Task>taskList){
        return taskList.stream()
                .map(TaskTransformer::taskToTaskResponse)
                .collect(Collectors.toList());
    }
                                            //Task Update Methods

    public String updateTask(String taskCode, String title, String description, LocalDateTime dueDate, Status status) {
        //Getting the user details from login information and mapping ticket and user
        String emailId=authenticationDetailsService.getAuthenticationDetails();
        User user=userService.getUserByEmailId(emailId);

        return taskUpdationProcessor(taskCode, user, title, description, dueDate, status);
    }
    public String updateTask(String taskCode, String userEmailId, String title, String description, LocalDateTime dueDate, Status status){
        //Getting the user details from login information and mapping ticket and user
        String emailId=authenticationDetailsService.getAuthenticationDetails();
        User adminUser=userService.getUserByEmailId(emailId);

        if(adminUser.getRole().equals(Role.USER)){
            throw new AccessDeniedException("Access denied. Only Admins have access to user profiles to add tasks");
        }

        User user=userService.getUserByEmailId(userEmailId);
        return taskUpdationProcessor(taskCode, user, title, description, dueDate, status);
    }
    private String taskUpdationProcessor(String taskCode, User user, String title, String description, LocalDateTime dueDate, Status status){
        List<Task>taskList=getFilteredTaskList(taskCode, user.getEmailId(), null,null,null,null);
        if(taskList.size()!=0){
            Task task=taskList.get(0);

            //updating task attributes according to the request
            if(title!=null)task.setTitle(title);
            if(description!=null)task.setDescription(description);
            if(dueDate!=null)task.setDueDate(dueDate);
            if(status!=null)task.setStatus(status);

            taskRepository.save(task);
            return "Task has been updated successfully";
        }else{
            throw new InvalidTaskCodeException("Invalid task or user");
        }
    }
                                           //Task Deletion Methods
    @Override
    public String deleteTasks(List<String> taskCodeList) {
        List<String>deletedTaskList=new ArrayList<>();
        List<String>nonDeletedTaskList=new ArrayList<>();

        //Getting the user details from login information and mapping ticket and user
        String emailId=authenticationDetailsService.getAuthenticationDetails();
        User user=userService.getUserByEmailId(emailId);
        for(String taskCode: taskCodeList){
            boolean res=taskDeletionProcessor(user, taskCode);
            if(res)deletedTaskList.add(taskCode);
            else nonDeletedTaskList.add(taskCode);
        }
        if(nonDeletedTaskList.size()==0){
            return "Tasks "+deletedTaskList+" have been deleted successfully";
        }else{
            throw new InvalidTaskCodeException("Not able to delete task(s) "+nonDeletedTaskList+" as task codes are invalid");
        }
    }
    @Override
    public String deleteTasks(List<String> taskCodeList, String userEmailId){
        //Getting the user details from login information and mapping ticket and user
        String emailId=authenticationDetailsService.getAuthenticationDetails();
        User adminUser=userService.getUserByEmailId(emailId);

        if(adminUser.getRole().equals(Role.USER)){
            throw new AccessDeniedException("Access denied. Only Admins have access to user profiles to add tasks");
        }

        User user=userService.getUserByEmailId(userEmailId);

        List<String>deletedTaskList=new ArrayList<>();
        List<String>nonDeletedTaskList=new ArrayList<>();
        for(String taskCode: taskCodeList){
            boolean res=taskDeletionProcessor(user, taskCode);
            if(res)deletedTaskList.add(taskCode);
            else nonDeletedTaskList.add(taskCode);
        }
        if(nonDeletedTaskList.size()==0){
            return "Tasks "+deletedTaskList+" have been deleted successfully";
        }else{
            throw new InvalidTaskCodeException("Not able to delete task(s) "+nonDeletedTaskList+" as task codes are invalid");
        }
    }

    private boolean taskDeletionProcessor(User user, String taskCode){
        List<Task>taskList=getFilteredTaskList(taskCode, user.getEmailId(), null,null,null,null);
        if(taskList.size()!=0){
            Task task=taskList.get(0);
            taskRepository.delete(task);
            return true;
        }else{
            return false;
        }
    }
}
